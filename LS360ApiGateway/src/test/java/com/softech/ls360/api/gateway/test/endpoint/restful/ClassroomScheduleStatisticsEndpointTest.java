package com.softech.ls360.api.gateway.test.endpoint.restful;

import com.softech.ls360.api.gateway.test.LS360ApiGatewayAbstractTest;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by muhammad.sajjad on 11/2/2016.
 */

public class ClassroomScheduleStatisticsEndpointTest extends LS360ApiGatewayAbstractTest {


    @Test
    public void testScheduleEndpoint(){
        RestTemplate mockRestTemplate = new RestTemplate();
        Map<String, String> requestData = new HashMap<>();
        requestData.put("courseGuid", "c18795ea31824ccf8161881f5c6fa2da");
        HttpEntity request = new HttpEntity<>(requestData);
        String location = "http://localhost:8080/LS360ApiGateway/classroom/schedule";
        mockRestTemplate.postForLocation(location, request);
    }
}
