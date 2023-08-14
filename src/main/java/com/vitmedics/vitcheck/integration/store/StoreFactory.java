package com.vitmedics.vitcheck.integration.store;

import com.vitmedics.vitcheck.integration.store.shopify.ShopifyApiClient;
import com.vitmedics.vitcheck.integration.store.shopify.ShopifyApiClientV2;
import com.vitmedics.vitcheck.model.enumeration.storeType.StoreType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StoreFactory {

    private static final Logger log = LogManager.getLogger(StoreFactory.class);

    @Autowired
    private List<StoreApiClient> storeApiClients;

    public Optional<StoreApiClient> getStoreApiClient(StoreType storeType) {
        if(storeType == null)
            return null;

        switch (storeType) {
            case SHOPIFY: return storeApiClients.stream()
                    .filter(client -> client instanceof ShopifyApiClient)
                    .findFirst();

            case SHOPIFY_V2: return storeApiClients.stream()
                    .filter(client -> client instanceof ShopifyApiClientV2)
                    .findFirst();

            default: {
                log.error(String.format("Unknown StoreType instantiation requested: %s", storeType));
                return Optional.empty();
            }
        }
    }
}
