package com.merchant.api.controller;

import com.merchant.api.model.Application;
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
import com.merchant.api.service.ApplicationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/app")
public class ApplicationController {
	
    private final Logger log = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private ApplicationService service;

    @ApiOperation(value = "tambah data ke merchant", response = String.class)
    @PostMapping(value = "/test/new-data", produces = {"application/json"})
    public ResponseEntity<?> postNewData(
            @ApiParam(value = "Application Rrequest", required = true)
            @RequestBody(required = true) ApplicationReq request) {

        String result = service.saveAll(request);
        log.debug("result : {}", result);
        if(Objects.nonNull(result) && result.equalsIgnoreCase("success")) {
                return new ResponseEntity("{\"message\":\"successfully saved.\"}", HttpStatus.OK); 
        } else {
                return new ResponseEntity("{\"message\":\""+result+"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
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
    @GetMapping(value = "/test/getbyid")
    public ResponseEntity<?> getById(
            @ApiParam(value = "Order id from client", required = true)
            @RequestParam(required = true, value = "orderId") String orderId) {
        Application app = (Application) service.findByOrderId(orderId);
        log.debug("application created : {}", app);
        if(app == null) {
            return new ResponseEntity("{\"message\":\"Data not found with order id : "+ orderId +".\"}", HttpStatus.OK);
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
}
