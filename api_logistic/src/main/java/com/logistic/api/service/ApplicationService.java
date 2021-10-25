/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logistic.api.service;

import com.logistic.api.model.History;
import com.logistic.api.model.Item;
import com.logistic.api.model.TrxApplication;
import com.logistic.api.payload.request.PickupReq;
import com.logistic.api.payload.response.PickupResponse;
import com.logistic.api.payload.response.UpdateHistoryResponse;
import com.logistic.api.repository.ApplicationRepository;
import com.logistic.api.repository.HistoryRepository;
import com.logistic.api.repository.ItemsRepository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author PROSIA
 */
@Service
@Transactional(noRollbackFor = {Exception.class})
public class ApplicationService {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private HistoryRepository historyRepository;
    
    public PickupResponse saveNewApplication(PickupReq data) {
        PickupResponse response = new PickupResponse();
        try {
            TrxApplication application = new TrxApplication();
            application.setAddressFrom(data.getFrom());
            application.setAddressTo(data.getTo());
            application.setHeight(data.getHeight());
            application.setWeight(data.getWeight());
            application.setWidth(data.getWidth());
            application.setStatus(TrxApplication.Status.REQUEST_PICKUP);
            String noResi = ("R"+ new SimpleDateFormat("ddMMyy").format(new Date()) 
                    + ThreadLocalRandom.current().nextInt()).replace("-", "");
            application.setNoResi(noResi);
            application.setTransactionId(data.getTransactionId());
            application.setOrderNo(data.getOrderId());
            application.setTotalPrice(Double.valueOf(data.getTotalPrice()));
            application.setDeliveryPrice((double) 10000);
            applicationRepository.save(application);
            
            List<Item> itemList = new ArrayList<>();
            data.getItems().stream().map((it) -> {
                Item item = new Item();
                item.setApplication(application);
                item.setItemId(it.getItemId());
                item.setItemImageUrl(it.getItemImageUrl());
                item.setItemName(it.getItemName());
                item.setItemPrice(it.getItemPrice());
                item.setItemQuantity(it.getItemQuantity());
                item.setItemType(it.getItemType());
                item.setItemUrl(it.getItemUrl());
                item.setSellerBadge(it.getSellerBadge());
                item.setSellerId(it.getSellerId());
                item.setSellerName(it.getSellerName());
                return item;                
            }).forEachOrdered((item) -> {
                itemList.add(item);
            });
            itemsRepository.saveAll(itemList);
            
            History history = new History();
            history.setApplication(application);
            history.setDateHistory(new Date());
            history.setHistory("Penjual merequest pick up.");
            historyRepository.save(history);
            
            response.setContractCode(data.getContractCode());
            response.setFrom(application.getAddressFrom());
            response.setHeight(application.getHeight());
            response.setItems(data.getItems());
            response.setNoResi(application.getNoResi());
            response.setOrderId(application.getOrderNo());
            response.setStatusRequest(application.getStatus().toString());
            response.setTo(application.getAddressTo());
            response.setTotalPrice(String.valueOf(application.getTotalPrice().intValue()));
            response.setTransactionId(application.getTransactionId());
            response.setWeight(application.getWeight());
            response.setWidth(application.getWidth());
            
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new PickupResponse();
        }
    }
    
    public TrxApplication getApplicationByNoResi(String noResi) {
        try {
            TrxApplication application = new TrxApplication();
            return application = applicationRepository.findByNoResi(noResi);
        } catch (Exception e) {
            return new TrxApplication();
        }
    }
    
    public UpdateHistoryResponse updateHistory(TrxApplication application) {
        History history = new History();
        switch (application.getStatus()) {
            case REQUEST_PICKUP:
                application.setStatus(TrxApplication.Status.PICKUP_BY_CURRIER);
                applicationRepository.save(application);
                history.setApplication(application);
                history.setHistory("Barang diambil kurir.");
                history.setDateHistory(new Date());
                historyRepository.save(history);
                break;
            case PICKUP_BY_CURRIER:
                application.setStatus(TrxApplication.Status.ARRIVED_DROP_POINT);
                applicationRepository.save(application);
                history.setApplication(application);
                history.setHistory("Barang sampai di drop point.");
                history.setDateHistory(new Date());
                historyRepository.save(history);
                break;
            case ARRIVED_DROP_POINT:
                application.setStatus(TrxApplication.Status.DELIVERY_TO_CUSTOMER);
                applicationRepository.save(application);
                history.setApplication(application);
                history.setHistory("Barang sedang dikirim ke pembeli.");
                history.setDateHistory(new Date());
                historyRepository.save(history);
                break;
            case DELIVERY_TO_CUSTOMER:
                application.setStatus(TrxApplication.Status.DELIVERED);
                applicationRepository.save(application);
                history.setApplication(application);
                history.setHistory("Barang diterima pembeli.");
                history.setDateHistory(new Date());
                historyRepository.save(history);
                break;
            default:
                history.setHistory("Barang diterima pembeli.");
                break;
        }
        
        return new UpdateHistoryResponse(history.getHistory(), 
                application.getStatus().toString(), application.getNoResi());
    }
    
    public List<History> getAllHistory(Long appId) {
        return historyRepository.findAllHistoryByAppId(appId);
    }
}
