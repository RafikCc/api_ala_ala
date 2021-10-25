package com.merchant.api.controller;

import com.merchant.api.model.Application;
import com.merchant.api.model.CustomerInfo;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merchant.api.model.payload.request.ApplicationReq;
import com.merchant.api.model.payload.request.DeviceReturnRequest;
import com.merchant.api.model.payload.request.UpdateReq;
import com.merchant.api.model.payload.response.HistoryResponse;
import com.merchant.api.service.ApplicationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/app")
public class ApplicationController {
	
    private final Logger log = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private ApplicationService service;

    @ApiOperation(value = "tambah data ke merchant dan empower API", response = String.class)
    @PostMapping(value = "/test/new-data", produces = {"application/json"})
    public ResponseEntity<?> postNewData(
            @ApiParam(value = "Application Rrequest", required = true)
            @RequestBody(required = true) ApplicationReq request) {

        ResponseEntity result = service.saveAll(request, "ADD_DATA");
        log.debug("result : {}", result);
        if(result.getStatusCode().equals(HttpStatus.CREATED)) {
                return new ResponseEntity(result.getBody(), HttpStatus.OK); 
        } else {
                return new ResponseEntity(result.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @ApiOperation(value = "tambah data ke merchant", response = String.class)
    @PostMapping(value = "/add-data", produces = {"application/json"})
    public ResponseEntity<?> addData(
            @ApiParam(value = "Application Rrequest", required = true)
            @RequestBody(required = true) ApplicationReq request) {

        ResponseEntity result = service.saveAll(request, "NEW_DATA");
        log.debug("result : {}", result);
        if(result.getStatusCode().equals(HttpStatus.CREATED)) {
                return new ResponseEntity(result.getBody(), HttpStatus.OK); 
        } else {
                return new ResponseEntity(result.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "test API online with auth", response = String.class)
    @GetMapping(value = "/test/online")
    public ResponseEntity<String> getOnline() {
        return ResponseEntity.ok("You are online");
    }

    @ApiOperation(value = "update data trx")
    @PostMapping(value = "/update", produces = {"application/json"})
    public ResponseEntity<?> update(
            @ApiParam(value = "Update Request", required = true)
            @RequestBody UpdateReq req) {
        Application app = (Application) service.findByOrderId(req.getOrderId());
        if(app == null) {
            return new ResponseEntity("{\"message\":\"No data found for order id : "+req.getOrderId()+"\"}", 
                    HttpStatus.BAD_REQUEST);
        } else {
            app.setStatus(Application.Status.valueOf(req.getStatus()));
            String result = service.saveApp(app);
            if(result.equalsIgnoreCase("success")) {
                return new ResponseEntity("{\"message\":\"Data has been updated.\"}", HttpStatus.CREATED);
            } else {
                return new ResponseEntity("{\"message\":\"Error update data.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    
    @ApiOperation(value = "get data by order id", response = Application.class)
    @GetMapping(value = "/{orderId}")
    public ResponseEntity<?> getById(
            @ApiParam(value = "Order id from client", required = true)
            @PathVariable(required = true, value = "orderId") String orderId) {
        Application app = (Application) service.findByOrderId(orderId);
        if(app == null) {
            return new ResponseEntity("{\"message\":\"Data not found with order id : "+ orderId +".\"}", HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity(app, HttpStatus.OK);
    }
    
    @ApiOperation(value = "update item status return", response = String.class)
    @PostMapping(value = "/device-return", produces = {"application/json"})
    public ResponseEntity<String> updateDeviceReturn(
            @ApiParam(value = "DeviceReturnRequest", required = true)
            @RequestBody(required = true) DeviceReturnRequest request) {
        Application app = (Application) service.findByOrderId(request.getOrderId());
        
        if(app == null) {
            return new ResponseEntity("{\"message\":\"Data not found with order id : "
                    + request.getOrderId() +".\"}", HttpStatus.BAD_REQUEST);
        }
        String result = service.updateDeviceReturn(app, request.getItems(), request.getStatusItems());
        if(result.equalsIgnoreCase("success")) {
            return new ResponseEntity<>("{\"message\":\"Successfully update data.\"}", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"message\":\""+result+"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @ApiOperation(value = "get user transaction history")
    @GetMapping(value = "/transaction-history", produces = {"application/json"})
    public ResponseEntity<HistoryResponse> getTransactionHistory(
            @ApiParam(value = "Ktp No from customer", required = true)
            @RequestParam(required = true) String noKtp,
            @ApiParam(value = "Phone No from customer", required = true)
            @RequestParam(required = true) String noHp) {
        List<CustomerInfo> customers = service.getCustByNoKtp(noKtp, noHp);
        if(customers.size() < 1) {
            return new ResponseEntity("{\"message\":\"Data not found with ktp number : "
                    + noKtp +" and phone number : " + noHp + ".\"}", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            return new ResponseEntity(service.getHistory(customers.get(0)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
