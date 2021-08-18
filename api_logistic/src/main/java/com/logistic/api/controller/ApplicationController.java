/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logistic.api.controller;

import com.logistic.api.model.History;
import com.logistic.api.model.TrxApplication;
import com.logistic.api.payload.request.InquiryRequest;
import com.logistic.api.payload.request.PickupReq;
import com.logistic.api.payload.response.HistoryResponse;
import com.logistic.api.payload.response.InquiryResponse;
import com.logistic.api.payload.response.PickupResponse;
import com.logistic.api.payload.response.UpdateHistoryResponse;
import com.logistic.api.service.ApplicationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PROSIA
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/delivery")
public class ApplicationController {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ApplicationService applicationService;
    
    @ApiOperation(value = "Test online with auth", response = String.class)
    @GetMapping(value = "/test-online", produces = {"application/json"})
    public ResponseEntity<String> testOnline() {
        return new ResponseEntity("{\"message\": \"API is online.\"}", HttpStatus.OK);
    }
    
    @ApiOperation(value = "request new pick up", response = PickupResponse.class)
    @PostMapping(value = "/request-pick-up")
    public ResponseEntity<PickupResponse> newRequest(
            @ApiParam(value = "Pickup Request", required = true)
            @RequestBody PickupReq req) {
        PickupResponse response = new PickupResponse();
        try {
            response = applicationService.saveNewApplication(req);
            return new ResponseEntity(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity("{\"message\": \"Error save data.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @ApiOperation(value = "update history delivery", response = UpdateHistoryResponse.class)
    @PostMapping(value = "/update-history")
    public ResponseEntity<UpdateHistoryResponse> updateHistory(
            @ApiParam(value = "No Resi", required = true)
            @RequestParam(required = true) String noResi) {
        UpdateHistoryResponse response = new UpdateHistoryResponse();
        TrxApplication application = applicationService.getApplicationByNoResi(noResi);
        if(application == null) {
            return new ResponseEntity("{\"message\": \"Application not found with no resi : "
                    + noResi +".\"}", HttpStatus.BAD_REQUEST);
        }
        try {
            response = applicationService.updateHistory(application);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("{\"message\": \"Error update history delivery.\"}", 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @ApiOperation(value = "get status delivery", response = InquiryResponse.class)
    @PostMapping(value = "/inquiry")
    public ResponseEntity<InquiryResponse> getDataDelivery(
            @ApiParam(value = "inquiry request", required = true)
            @RequestBody(required = true) InquiryRequest request) {
        TrxApplication application = new TrxApplication();
        application = applicationService.getApplicationByNoResi(request.getNoResi());
        if(application == null) {
            return new ResponseEntity("{\"message\": \"Data not found with no resi : " 
                    + request.getNoResi() +".\"}", HttpStatus.BAD_REQUEST);
        }
        InquiryResponse response = new InquiryResponse();
        response.setContractCode(request.getContractCode());
        response.setItems(request.getItems());
        response.setNoResi(request.getNoResi());
        response.setOrderId(request.getOrderId());
        response.setStatusDelivery(application.getStatus().toString());
        response.setTotalPrice(request.getTotalPrice());
        response.setTransactionId(request.getTransactionId());
        return new ResponseEntity(response, HttpStatus.OK);
    }
    
    @ApiOperation(value = "get all history delivery", response = InquiryResponse.class)
    @GetMapping(value = "/history")
    public ResponseEntity<List<HistoryResponse>> getAllHistory(
            @ApiParam(value = "No Resi", required = true)
            @RequestParam(required = true) String noResi) {
        TrxApplication application = applicationService.getApplicationByNoResi(noResi);
        if(application == null) {
            return new ResponseEntity("{\"message\": \"Application not found with no resi : "
                    + noResi +".\"}", HttpStatus.BAD_REQUEST);
        }
        try {
            List<History> histories = applicationService.getAllHistory(application.getAppId());
            List<HistoryResponse> response = new ArrayList<>();
            histories.forEach(x -> {
                response.add(new HistoryResponse(x.getHistory(), x.getDateHistory()));
            });
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("{\"message\": \"Error find history delivery.\"}", 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
