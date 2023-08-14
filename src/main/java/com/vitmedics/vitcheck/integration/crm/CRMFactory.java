package com.vitmedics.vitcheck.integration.crm;

import com.vitmedics.vitcheck.integration.crm.klaviyo.KlaviyoService;
import com.vitmedics.vitcheck.integration.crm.mailchimp.MailchimpService;
import com.vitmedics.vitcheck.model.enumeration.crmType.CRMType;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CRMFactory {

    private static final Logger log = LogManager.getLogger(CRMFactory.class);

    @Autowired
    private List<CRMService> CRMServices;

    public Optional<CRMService> getCRMService(CRMType crmType) {
        if(crmType == null)
            return null;

        switch (crmType) {
            case MAILCHIMP: return CRMServices.stream()
                    .filter(client -> client instanceof MailchimpService)
                    .findFirst();

            case KLAVIYO: return CRMServices.stream()
                    .filter(client -> client instanceof KlaviyoService)
                    .findFirst();

            default:
                log.error(String.format("Unknown CRMType instantiation requested: %s", crmType));
                return Optional.empty();
        }
    }
}
