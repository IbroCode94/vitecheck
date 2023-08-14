package com.vitmedics.vitcheck.integration.crm.mailchimp;

import com.ecwid.maleorang.MailchimpClient;
import com.ecwid.maleorang.MailchimpException;
import com.ecwid.maleorang.MailchimpObject;
import com.ecwid.maleorang.method.v3_0.lists.members.EditMemberMethod;
import com.ecwid.maleorang.method.v3_0.lists.members.MemberInfo;
import com.vitmedics.vitcheck.dto.model.survey.PrescribedProductDto;
import com.vitmedics.vitcheck.integration.crm.CRMService;
import com.vitmedics.vitcheck.model.UserDetailsModel;
import com.vitmedics.vitcheck.model.entities.survey.SurveyInstallation;
import com.vitmedics.vitcheck.model.entities.survey.SurveySubmission;
import com.vitmedics.vitcheck.model.entities.survey.SurveySubmissionResponse;
import com.vitmedics.vitcheck.model.enumeration.crmSetting.CRMSetting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MailchimpService implements CRMService {

    private static final Logger log = LogManager.getLogger(MailchimpService.class);

    /**
     * Save contact data and submission responses to Mailchimp
     * @param submission
     * @param prescribedProducts
     */
    public void saveContactData(SurveySubmission submission, UserDetailsModel userDetails, Set<PrescribedProductDto> prescribedProducts) {

        SurveyInstallation installation = submission.getSurveyInstallation();

        // Create a map from the settings for convenience
        Map<CRMSetting, String> settings = installation.getSurveyInstallationCrmSettings()
                .stream()
                .collect(Collectors.toMap(i -> i.getCrmSetting(), i -> i.getValue()));

        if(!isConfigValid(settings, installation.getId())) {
            log.error(String.format("Invalid Mailchimp Configuration for installation %s. Skipping CRM contact sync", installation.getId()));
            return;
        }

        try(MailchimpClient client = new MailchimpClient(settings.get(CRMSetting.MAILCHIMP_API_KEY))) {

            String audienceId = settings.get(CRMSetting.MAILCHIMP_AUDIENCE_ID);
            Map<String, String> mergeFields = buildMergeFields(submission, userDetails, settings);

            EditMemberMethod.CreateOrUpdate method = new EditMemberMethod.CreateOrUpdate(audienceId, userDetails.getEmail());
            method.status = userDetails.isOptedIn() ? "subscribed" : "unsubscribed";
            method.merge_fields = new MailchimpObject();
            method.merge_fields.mapping.putAll(mergeFields);

            try {
                MemberInfo member = client.execute(method);
            }
            catch (MailchimpException e) {
                log.error(String.format("Error saving Mailchimp contact for submission id %s", submission.getId()), e.getMessage());
            }
        }
        catch (Exception ex) {
            log.error("Error saving contact data to Mailchimp", ex);
        }
    }

    private HashMap<String,String> buildMergeFields(SurveySubmission submission, UserDetailsModel userDetails, Map<CRMSetting, String> settings) {
        HashMap<String, String> fields = new HashMap<>();

        String medsFieldKey = settings.get(CRMSetting.MAILCHIMP_DATA_FIELD_MEDS_KEY);
        String questionFieldPrefix = settings.get(CRMSetting.MAILCHIMP_DATA_FIELD_QUESTION_PREFIX);

        fields.put("FNAME", userDetails.getFirstName());
        fields.put("LNAME", userDetails.getLastName());
        fields.put(medsFieldKey, getMedicationsFieldValue(submission));
        fields.putAll(getSurveyResponseFieldValues(submission, questionFieldPrefix));

        return fields;
    }

    private String getMedicationsFieldValue(SurveySubmission submission) {
        return submission.getMedications()
                .stream()
                .map(medication -> String.format("%s (%s)", medication.getMedication().getName(), medication.getDurationTaken()))
                .collect(Collectors.joining(","));
    }

    private Map<String, String> getSurveyResponseFieldValues(SurveySubmission submission, String mergeFieldPrefix) {

        return submission.getResponses()
                .stream()
                .map(r -> mapResponseToMergeFieldValue(r, mergeFieldPrefix))
                .collect(Collectors.toMap(field -> field.getKey(), field -> field.getValue()));
    }

    private AbstractMap.SimpleEntry<String, String> mapResponseToMergeFieldValue(SurveySubmissionResponse response, String mergeFieldPrefix) {

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

        String key = String.format("%s%s",mergeFieldPrefix, response.getQuestion().getId());
        return new AbstractMap.SimpleEntry<>(key, s.toString());
    }

    private boolean isConfigValid(Map<CRMSetting, String> settings, UUID installationId) {

        boolean isValid = true;

        if(!settings.containsKey(CRMSetting.MAILCHIMP_API_KEY) || Strings.isBlank(settings.get(CRMSetting.MAILCHIMP_API_KEY)))
        {
            log.error(String.format("MAILCHIMP_API_KEY not set for installation id %s.", installationId));
            isValid = false;
        }

        if(!settings.containsKey(CRMSetting.MAILCHIMP_AUDIENCE_ID) || Strings.isBlank(settings.get(CRMSetting.MAILCHIMP_AUDIENCE_ID)))
        {
            log.error(String.format("MAILCHIMP_AUDIENCE_ID not set for installation id %s.", installationId));
            isValid = false;
        }

        if(!settings.containsKey(CRMSetting.MAILCHIMP_DATA_FIELD_MEDS_KEY) || !settings.containsKey(CRMSetting.MAILCHIMP_DATA_FIELD_QUESTION_PREFIX))
        {
            log.error(String.format("Mailchimp Data Field Config missing for installation id %s.", installationId));
            isValid = false;
        }

        return isValid;
    }
}
