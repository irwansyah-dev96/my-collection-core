package io.irwansyahdev96.readcollection.base.dao;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.irwansyahdev96.readcollection.base.dto.res.BaseResSingleDto;
import io.irwansyahdev96.readcollection.base.dto.res.BaseTransactionResDto;

public class BaseClient {
    
    public String ping(String server){
        RestTemplate restTemplate = new RestTemplate();
        
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(server.concat("actuator/health"), String.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> get(String url){
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, Map.class);
    }

    public BaseTransactionResDto put(String url, Object requestBody){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> reqEntity = new HttpEntity<>(requestBody, headers);

        try {
            return restTemplate
                .exchange(url, HttpMethod.PUT, reqEntity, new ParameterizedTypeReference<BaseTransactionResDto>() {}).getBody();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public BaseResSingleDto<?> get(String url, Class<?> clazz){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> reqEntity = new HttpEntity<>(null, headers);

        try {
            return restTemplate.exchange(
                url,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<BaseResSingleDto<?>>() {}
            ).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
