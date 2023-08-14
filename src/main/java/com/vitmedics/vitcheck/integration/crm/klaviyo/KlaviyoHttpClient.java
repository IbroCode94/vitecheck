package com.vitmedics.vitcheck.integration.crm.klaviyo;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="didClient", url = "${vitcheck.integration.crm.klaviyo.apiEndpoint}")
public interface KlaviyoHttpClient {

    @RequestMapping(method = RequestMethod.GET, value = "/identify")
    @Headers("Accept: 'text/html'")
    String identify(@RequestParam(value="data") String base64EncodedData);


}
