package com.vitmedics.vitcheck.integration.crm.klaviyo;

import com.vitmedics.vitcheck.dto.model.survey.PrescribedProductDto;
import com.vitmedics.vitcheck.integration.crm.CRMService;
import com.vitmedics.vitcheck.model.PrescribedProduct;
import com.vitmedics.vitcheck.model.UserDetailsModel;
import com.vitmedics.vitcheck.model.entities.survey.SurveyInstallation;
import com.vitmedics.vitcheck.model.entities.survey.SurveySubmission;
import com.vitmedics.vitcheck.model.entities.survey.SurveySubmissionResponse;
import com.vitmedics.vitcheck.model.enumeration.crmSetting.CRMSetting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KlaviyoService implements CRMService {

    private static final Logger log = LogManager.getLogger(KlaviyoService.class);

    @Autowired
    private KlaviyoHttpClient klaviyoHttpClient;

    /**
     * Save contact data and submission responses to Mailchimp
     * @param submission
     * @param prescribedProducts
     */
    @Override
    public void saveContactData(SurveySubmission submission, UserDetailsModel userDetails, Set<PrescribedProductDto> prescribedProducts) {

        SurveyInstallation installation = submission.getSurveyInstallation();

        // Create a map from the settings for convenience
        Map<CRMSetting, String> settings = installation.getSurveyInstallationCrmSettings()
                .stream()
                .collect(Collectors.toMap(i -> i.getCrmSetting(), i -> i.getValue()));

        if(!isConfigValid(settings, installation.getId())) {
            log.error(String.format("Invalid Klaviyo Configuration for installation %s. Skipping CRM contact sync", installation.getId()));
            return;
        }

        try {
            // Add the core properties
            JSONObject propertiesJson = new JSONObject()
                    .put("$email", userDetails.getEmail())
                    .put("$first_name", userDetails.getFirstName())
                    .put("$last_name", userDetails.getLastName());

            // Set opted in state if opted in
            if(userDetails.isOptedIn()) {
                propertiesJson.put("$consent", new JSONArray().put("email"));
            }

            // Add survey responses to the properties
            submission.getResponses()
                    .stream()
                    .forEach(response -> {
                        AbstractMap.SimpleEntry<String, String> responseFieldNameAndValue = mapResponseToMergeFieldValue(response);
                        propertiesJson.put(responseFieldNameAndValue.getKey(), responseFieldNameAndValue.getValue());
                    });

            // Add medications to the properties
            String medications = submission.getMedications()
                    .stream()
                    .map(medication -> String.format("%s (%s)", medication.getMedication().getName(), medication.getDurationTaken()))
                    .collect(Collectors.joining(","));

            propertiesJson.put("VC_meds", medications);

            // Add prescribed items
            JSONArray productsJson = new JSONArray();
            for (PrescribedProductDto product : prescribedProducts) {
                productsJson.put(new JSONObject()
                        .put("id", product.getExternalId())
                        .put("type", product.getProductType())
                        .put("name", product.getName()));
            }

            propertiesJson.put("VC_prescription", productsJson);

            // Build the final JSON object to be encoded and sent to Klaviyo
            JSONObject dataJson = new JSONObject()
                    .put("token", settings.get(CRMSetting.KLAVIYO_API_KEY))
                    .put("properties", propertiesJson);

            // Encode the JSON string to base64
            String requestData = Base64.getEncoder().encodeToString(dataJson.toString().getBytes());

            try {
                String result = klaviyoHttpClient.identify(requestData);

                // Result:  1 == Success, 0 == Failure
                if(result == "0") {
                    log.error(String.format("Error saving Klaviyo contact for submission id %s", submission.getId()));
                }
            }
            catch (Exception e) {
                log.error(String.format("Error saving Klaviyo contact for submission id %s", submission.getId()), e.getMessage());
            }
        }
        catch (Exception ex) {
            log.error("Error saving contact data to Klaviyo", ex);
        }
    }

    private AbstractMap.SimpleEntry<String, String> mapResponseToMergeFieldValue(SurveySubmissionResponse response) {

        StringBuilder s = new StringBuilder();

        if(Strings.isNotBlank(response.getResponseText1())) {
            s.append(String.format("%s ", response.getResponseText1()));
        }

        if(Strings.isNotBlank(response.getResponseText2())) {
            s.append(String.format("%s ", response.getResponseText2()));
        }

        if(response.getAnswers().size() > 0) {

            switch(response.getQuestion().getQuestionType()) {
                case HEIGHT:
                    s.append(Strings.isBlank(response.getResponseText2()) ? "Metres" : "FeetInches");
                    break;
                case WEIGHT:
                    s.append(Strings.isBlank(response.getResponseText2()) ? "Kgs" : "StoneLbs");
                    break;
                default:
                    s.append(response.getAnswers().stream().map(a -> a.getLabel()).collect(Collectors.joining(",")));
                    break;
            }
        }

        String formattedFieldName = response.getQuestion().getName().toLowerCase().replace(" ", "_");
        String key = String.format("%s%s", "VC_", formattedFieldName);
        return new AbstractMap.SimpleEntry<>(key, s.toString());
    }

    private boolean isConfigValid(Map<CRMSetting, String> settings, UUID installationId) {

        boolean isValid = true;

        if(!settings.containsKey(CRMSetting.KLAVIYO_API_KEY) || Strings.isBlank(settings.get(CRMSetting.KLAVIYO_API_KEY)))
        {
            log.error(String.format("KLAVIYO_API_KEY not set for installation id %s.", installationId));
            isValid = false;
        }

        return isValid;
    }
}
