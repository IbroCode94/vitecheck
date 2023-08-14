package com.vitmedics.vitcheck.integration.store.shopify;

import com.vitmedics.vitcheck.integration.store.StoreApiClient;
import com.vitmedics.vitcheck.model.PrescribedProduct;
import com.vitmedics.vitcheck.model.entities.survey.SurveyInstallationStoreSetting;
import com.vitmedics.vitcheck.model.enumeration.productType.ProductType;
import com.vitmedics.vitcheck.model.enumeration.storeSetting.StoreSetting;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Service
public class ShopifyApiClientV2 implements StoreApiClient {

    private static final Logger log = LogManager.getLogger(ShopifyApiClientV2.class);

    @Autowired
    private SpringTemplateEngine templateEngine;

    private final String GET_PRODUCTS_REQUEST_TEMPLATE_NAME = "shopify/getProducts-request.txt";
    private final String METAFIELD_NAMESPACE = "vitcheck";
    private final String PRODUCT_TYPE_METAFIELD_KEY = "product_type";
    private final String PRESCRIPTION_METAFIELD_KEY = "prescription";
    private final String VARIANT_ID_METAFIELD_KEY = "variant_id";
    private final String SELLING_PLAN_ID_METAFIELD_KEY = "selling_plan_id";


    /**
     * Retrieve a list of products from Shopify for the provided installation settings
     * @param storeSettings
     * @return
     * @throws IOException
     */
    @Override
    public Set<PrescribedProduct> getProducts(@NotNull Set<SurveyInstallationStoreSetting> storeSettings) throws IOException {

        if(storeSettings == null)
            throw new IllegalArgumentException("Store Settings is required");

        String url = storeSettings
                .stream()
                .filter(s -> s.getStoreSetting() == StoreSetting.SHOPIFY_BASE_URL)
                .findFirst()
                .map(s -> s.getValue())
                .orElseThrow(() -> new IllegalArgumentException("Shopify Base URL not set"));

        String accessToken = storeSettings
                .stream()
                .filter(s -> s.getStoreSetting() == StoreSetting.SHOPIFY_STOREFRONT_ACCESS_TOKEN)
                .findFirst()
                .map(s -> s.getValue())
                .orElseThrow(() -> new IllegalArgumentException("Shopify Storefront Access Token not set"));


        // Load the graph query from a static template - keep it simple, since not dynamic
        Context context = new Context();
        String requestBody = templateEngine.process(GET_PRODUCTS_REQUEST_TEMPLATE_NAME, context);

        try(CloseableHttpClient httpclient = HttpClients.createDefault()){

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/graphql");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("X-Shopify-Storefront-Access-Token", accessToken);

            StringEntity entity = new StringEntity(requestBody);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {

                StatusLine status = response.getStatusLine();

                if(status.getStatusCode() == HttpStatus.SC_OK) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    return mapResponseBodyToProductList(responseBody);
                }
                else
                    throw new HttpResponseException(status.getStatusCode(), status.getReasonPhrase());
            }
        }
    }

    /**
     * Perform mapping of the JSON string returned in the API call to a
     * List of PrescribedProduct
     * @param dataStr
     * @return prescribed products
     */
    private Set<PrescribedProduct> mapResponseBodyToProductList(String dataStr) {

        HashSet<PrescribedProduct> mappedProducts = new HashSet<>();

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(dataStr);

            JSONObject data = (JSONObject) json.get("data");
            JSONObject vitcheckCollection = (JSONObject) data.get("collectionByHandle");

            // Get Products list
            JSONArray products = (JSONArray) getChildren((JSONObject) vitcheckCollection.get("products"));

            Iterator i = products.iterator();

            // Iterate over them and map to a prescribed product
            while (i.hasNext()) {

                JSONObject product = (JSONObject) i.next();

                // Obtain list of metafields and variants
                JSONArray metafields = getChildren((JSONObject) product.get("metafields"));
                JSONArray variants = getChildren((JSONObject) product.get("variants"));

                Optional<String> productTypeStr = getMetafieldValueByKey(PRODUCT_TYPE_METAFIELD_KEY, metafields);

                // Use the first variant by default
                Optional<JSONObject> defaultVariant = variants.stream().findFirst();

                // If product type not defined then can't map to vitcheck prescription, ignore
                // Should always be at least the default variant, ignore if there isn't at least one
                if(productTypeStr.isEmpty() || defaultVariant.isEmpty())
                    continue; // ignore these products

                Optional<ProductType> productType = mapProductTypeString(productTypeStr.get());

                // If the prescribed productTypeStr doesn't match an ENUM value then skip the product
                if(productType.isEmpty())
                    continue;

                // If a custom id has been specified (will be in raw id form)
                // then check the variant exists and use the id
                // If it doesn't exist, then use the raw id from the default variant
                Optional<String> customVariantRawId = getMetafieldValueByKey(VARIANT_ID_METAFIELD_KEY, metafields);
                String id = customVariantRawId.isPresent() && variantExists(customVariantRawId.get(), variants)
                        ? customVariantRawId.get()
                        : getRawId((String)defaultVariant.get().get("id"));

                // Create the vitcheck native PrescribedProduct
                PrescribedProduct prescribedProduct = new PrescribedProduct(productType.get(), id, (String) product.get("title"));

                Optional<String> description = getMetafieldValueByKey(PRESCRIPTION_METAFIELD_KEY, metafields);
                prescribedProduct.setDescription(description.isPresent() ? description.get() : (String)product.get("descriptionHtml"));
                prescribedProduct.addProperty("handle", (String) product.get("handle"));

                Optional<String> sellingPlanId = getMetafieldValueByKey(SELLING_PLAN_ID_METAFIELD_KEY, metafields);

                if(sellingPlanId.isPresent()) {
                    prescribedProduct.addProperty("sellingPlanId", sellingPlanId.get());
                }

                mappedProducts.add(prescribedProduct);
            }
        }
        catch(ParseException ex) {
            log.error(String.format("Error mapping response to product list. Response json was: %s", dataStr), ex);
        }

        return mappedProducts;
    }

    /**
     * Given a parent object, get a list of children -> flattening edges -> node {} to {}
     * @param parent
     * @return
     */
    private JSONArray getChildren(JSONObject parent) {

        JSONArray children = new JSONArray();
        JSONArray edges = (JSONArray) parent.get("edges");

        Iterator i = edges.iterator();

        while (i.hasNext()) {
            JSONObject edge = (JSONObject) i.next();
            JSONObject node = (JSONObject) edge.get("node");
            children.add(node);
        }

        return children;
    }

    /**
     * Get a metafield by key from an array of metafield JSON objects
     * @param key
     * @param metafields
     * @return
     */
    private Optional<String> getMetafieldValueByKey(String key, JSONArray metafields) {

        Optional<JSONObject> metafield = (Optional<JSONObject>)metafields
                .stream()
                .filter(f -> ((String)((JSONObject)f).get("key")).trim().equals(key) && ((String)((JSONObject)f).get("namespace")).trim().equals(METAFIELD_NAMESPACE))
                .findFirst();

        if(metafield.isEmpty())
            return Optional.empty();

        String value = (String)metafield.get().get("value");
        return Optional.of(value.trim());
    }

    /**
     * Map from graph id format e.g. gid://shopify/Product/4730912604235
     * to the raw identifier
     * @param graphId
     * @return id
     */
    private String getRawId(String graphId) {
        String[] elements = graphId.split("/");
        String id = elements[elements.length-1];
        return id;
    }

    private boolean variantExists(String variantId, JSONArray variants) {
        return variants
                .stream()
                .anyMatch(f ->
                        getRawId((String)((JSONObject)f).get("id")).equals(variantId));
    }

    /**
     * Map string value of product type to ProductType Enum
     * @param productTypeStr
     * @return
     */
    private Optional<ProductType> mapProductTypeString(String productTypeStr) {

        try {
            ProductType pt = Enum.valueOf(ProductType.class, productTypeStr);
            return Optional.of(pt);
        }
        catch(Exception e) {
            log.error(String.format("Failed to map provided value %s to a valid ProductType enum value", productTypeStr));
            return Optional.of(null);
        }
    }
}
