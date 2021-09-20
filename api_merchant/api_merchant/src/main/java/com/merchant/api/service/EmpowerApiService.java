package com.merchant.api.service;

import ch.qos.logback.classic.Level;
import com.google.gson.JsonObject;
import com.merchant.api.model.payload.request.AuthRequest;
import com.merchant.api.model.payload.response.AuthResponse;
import com.merchant.api.model.payload.response.ErrorResponse;
import com.merchant.api.model.payload.response.MessageAndCode;
import com.merchant.api.model.payload.response.Meta;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public class EmpowerApiService {

    public String getToken(String username, String password, String url) {
        RestTemplate restTemplate = new RestTemplate();
        AuthRequest jwtRequest = new AuthRequest(username, password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", jwtRequest.getUsername());
        jsonObject.addProperty("password", jwtRequest.getPassword());
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        AuthResponse response = restTemplate.postForObject(url, request, AuthResponse.class);
        return "Bearer " + response.getToken();
    }
    
    public ResponseEntity<?> excute(String url, String requestBody, HttpMethod httpMethod, 
            Class<?> responseType, ErrorResponse errorResponse, String token) {
        ResponseEntity<?> response = null;
        List<MessageAndCode> message = null;
        Meta meta = new Meta();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", token);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(url, httpMethod, entity, responseType);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            message = new ArrayList<>();
            message.add(new MessageAndCode(e.getMessage(), Level.ERROR_INTEGER.toString()));
            errorResponse.setErrors(message);
            meta.setHttp_status(e.getStatusCode().toString());
            errorResponse.setMeta(meta);
            return new ResponseEntity(errorResponse, e.getStatusCode());
        }
        
        if (response != null) {
            return response;
        } else {
            return null;
        }
    }
}
