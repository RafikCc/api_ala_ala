package com.merchant.api.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merchant.api.model.AdditionalInfo;
import com.merchant.api.model.Application;
import com.merchant.api.model.Application.Status;
import com.merchant.api.model.BankInfo;
import com.merchant.api.model.CustomerAddress;
import com.merchant.api.model.CustomerAddress.AddressType;
import com.merchant.api.model.CustomerInfo;
import com.merchant.api.model.CustomerInfo.Gender;
import com.merchant.api.model.CustomerInfo.LastEducation;
import com.merchant.api.model.CustomerInfo.MaritalStatus;
import com.merchant.api.model.DocumentPhoto;
import com.merchant.api.model.DocumentPhoto.DocumentType;
import com.merchant.api.model.EmploymentInfo;
import com.merchant.api.model.EmploymentInfo.EmploymentType;
import com.merchant.api.model.EmploymentInfo.IndustryType;
import com.merchant.api.model.EmploymentInfo.Profession;
import com.merchant.api.model.Installment;
import com.merchant.api.model.Orders;
import com.merchant.api.model.OrderItems;
import com.merchant.api.model.RelativeContact;
import com.merchant.api.model.RelativeContact.RelativeRelation;
import com.merchant.api.model.payload.request.ApplicationReq;
import com.merchant.api.model.payload.request.CustomerAddressReq;
import com.merchant.api.model.payload.request.DocumentPhotoReq;
import com.merchant.api.model.payload.request.OrderItemsReq;
import com.merchant.api.model.payload.request.RelativeContactReq;
import com.merchant.api.repository.AdditionalInfoRepo;
import com.merchant.api.repository.ApplicationRepo;
import com.merchant.api.repository.BankInfoRepo;
import com.merchant.api.repository.CustomerAddressRepo;
import com.merchant.api.repository.CustomerInfoRepo;
import com.merchant.api.repository.DocumentPhotoRepo;
import com.merchant.api.repository.EmploymentInfoRepo;
import com.merchant.api.repository.InstallmentRepo;
import com.merchant.api.repository.OrderItemsRepo;
import com.merchant.api.repository.OrderRepo;
import com.merchant.api.repository.RelativeContactRepo;

@Service
@Transactional(readOnly = false, rollbackFor = {Exception.class})
public class ApplicationService {
	
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdditionalInfoRepo additionalInfoRepo;

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private BankInfoRepo bankInfoRepo;

    @Autowired
    private CustomerAddressRepo customerAddressRepo;

    @Autowired
    private CustomerInfoRepo customerInfoRepo;

    @Autowired
    private DocumentPhotoRepo documentPhotoRepo;

    @Autowired
    private EmploymentInfoRepo employmentInfoRepo;

    @Autowired
    private InstallmentRepo installmentRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemsRepo orderItemsRepo;

    @Autowired
    private RelativeContactRepo relativeContactRepo;
	
    public String saveAll(ApplicationReq request) {
        String result = "Success";
        Application application = new Application();
        application.setConsentTimeStamp(new Date());
        application.setExpiredTime(new Date(new Date().getTime() + 86400000));
        application.setOrderId("ORDER" + new SimpleDateFormat("MMYY").format(new Date()) 
                        + "00" + new SimpleDateFormat("HH").format(new Date()));
        application.setStatus(Status.NEW_REQUEST);
        try {
                applicationRepo.save(application);
        } catch (Exception e) {
                // TODO: handle exception
                log.debug("error save application : {}", e.getMessage());
                result = e.getMessage();
        }

        AdditionalInfo additionalInfo = new AdditionalInfo();
        additionalInfo.setCustomerRegistrationTime(new Date(new Date().getTime() - (158*60*60*1000)));
        additionalInfo.setNumberOfTransaction(10);
        additionalInfo.setOfferCode(request.getAdditionalInfo().getOfferCode());
        additionalInfo.setOtpVerificationTime(new Date(additionalInfo.getCustomerRegistrationTime().getTime() + 86400000));
        additionalInfo.setVolumeOfTransaction(new Double(10000000));
        additionalInfo.setUserId(new Long(194));
        additionalInfo.setApplication(application);
        try {
                additionalInfoRepo.save(additionalInfo);
        } catch (Exception e) {
                // TODO: handle exception
                log.debug("error save additional info : {}", e.getMessage());
                result = e.getMessage();
        }

        BankInfo bankInfo = new BankInfo();
        bankInfo.setBankCode(request.getBankInfo().getBankCode());
        bankInfo.setBankName(request.getBankInfo().getBankName());
        bankInfo.setAccountNumber(request.getBankInfo().getAccountNumber());
        bankInfo.setAccountName(request.getBankInfo().getAccountName());
        bankInfo.setApplication(application);
        try {
                bankInfoRepo.save(bankInfo);
        } catch (Exception e) {
                // TODO: handle exception
                log.debug("error save bank info : {}", e.getMessage());
                result = e.getMessage();
        }

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setDateOfBirth(request.getCustomerInfo().getDateOfBirth());
        customerInfo.setDependentNumber(request.getCustomerInfo().getDependentNumber());
        customerInfo.setEktpFlag(request.getCustomerInfo().getEktpFlag());
        customerInfo.setEmailAddress(request.getCustomerInfo().getEmailAddress());
        customerInfo.setFirstName(request.getCustomerInfo().getFirstName());
        customerInfo.setLastName(request.getCustomerInfo().getLastName());
        customerInfo.setGenderType(Gender.valueOf(request.getCustomerInfo().getGenderType()));
        customerInfo.setKtpNumber(request.getCustomerInfo().getKtpNumber());
        customerInfo.setLastEducation(LastEducation.valueOf(request.getCustomerInfo().getLastEducation()));
        customerInfo.setMaritalStatus(MaritalStatus.valueOf(request.getCustomerInfo().getMaritalStatus()));
        customerInfo.setMotherName(request.getCustomerInfo().getMotherName());
        customerInfo.setPhoneNumber(request.getCustomerInfo().getPhoneNumber());
        customerInfo.setBirthplaceCode(request.getCustomerInfo().getBirthplaceCode());
        customerInfo.setApplication(application);
        try {
                customerInfoRepo.save(customerInfo);
        } catch (Exception e) {
                // TODO: handle exception
                log.debug("error save cust info : {}", e.getMessage());
                result = e.getMessage();
        }

        List<CustomerAddress> listCustAddress = new ArrayList<CustomerAddress>();
        for(CustomerAddressReq address:request.getCustomerInfo().getCustomerAddress()) {
                CustomerAddress customerAddress = new CustomerAddress();
                customerAddress.setAddressType(AddressType.valueOf(address.getAddressType()));
                customerAddress.setCityCode(address.getCityCode());
                customerAddress.setDistrictCode(address.getDistrictCode());
                customerAddress.setStreetAddress(address.getStreetAddress());
                customerAddress.setVillageCode(address.getVillageCode());
                customerAddress.setRtNumber(address.getRtNumber());
                customerAddress.setRwNumber(address.getRwNumber());
                customerAddress.setZipcode(address.getZipcode());
                customerAddress.setCustInfoId(customerInfo);

                listCustAddress.add(customerAddress);
        }
        try {
                customerAddressRepo.saveAll(listCustAddress);
        } catch (Exception e) {
                // TODO: handle exception
                log.debug("error save cust address : {}", e.getMessage());
                result = e.getMessage();
        }

        List<DocumentPhoto> documentPhotos = new ArrayList<DocumentPhoto>();
        for(DocumentPhotoReq req:request.getDocumentPhoto()) {
                DocumentPhoto photo = new DocumentPhoto();
                photo.setDocumentType(DocumentType.valueOf(req.getDocumentType()));
                photo.setDocumentUrl(req.getDocumentUrl());
                photo.setApplication(application);

                documentPhotos.add(photo);
        }
        try {
                documentPhotoRepo.saveAll(documentPhotos);
        } catch (Exception e) {
                // TODO: handle exception
                log.debug("error save doc photo : {}", e.getMessage());
                result = e.getMessage();
        }

        EmploymentInfo employmentInfo = new EmploymentInfo();
        employmentInfo.setEmploymentType(EmploymentType.valueOf(request.getEmploymentInfo().getEmploymentType()));
        employmentInfo.setIndustryType(IndustryType.valueOf(request.getEmploymentInfo().getIndustryType()));
        employmentInfo.setLengthOfEmployment(request.getEmploymentInfo().getLengthOfEmployment());
        employmentInfo.setMonthlyIncome(request.getEmploymentInfo().getMonthlyIncome());
        employmentInfo.setOtherInstallment(request.getEmploymentInfo().getOtherInstallment());
        employmentInfo.setProfession(Profession.valueOf(request.getEmploymentInfo().getProfession()));
        employmentInfo.setApplication(application);
        try {
                employmentInfoRepo.save(employmentInfo);
        } catch (Exception e) {
                // TODO: handle exception
                log.debug("error save employment info : {}", e.getMessage());
                result = e.getMessage();
        }

        Installment installment = new Installment();
        installment.setMonthInstallment(request.getInstallment().getMonthInstallment());
        installment.setAmount(request.getInstallment().getAmount());
        installment.setApplication(application);
        try {
                installmentRepo.save(installment);
        } catch (Exception e) {
                // TODO: handle exception
                log.debug("error save employment info : {}", e.getMessage());
                result = e.getMessage();
        }

        Orders order = new Orders();
        order.setInvoiceNumber("INV" + new SimpleDateFormat("MMYY").format(new Date()) 
                        + "00" + new SimpleDateFormat("HH").format(new Date()));
        order.setShippingAddress(request.getOrder().getShippingAddress());
        order.setTotalPrice(request.getOrder().getTotalPrice());
        order.setApplication(application);
        try {
                orderRepo.save(order);
        } catch (Exception e) {
                // TODO: handle exception
                log.debug("error save order : {}", e.getMessage());
                result = e.getMessage();
        }

        List<OrderItems> items = new ArrayList<OrderItems>();
        for(OrderItemsReq req:request.getOrder().getItems()) {
                OrderItems oi = new OrderItems();
                oi.setItemId(req.getItemId());
                oi.setItemImageUrl(req.getItemImageUrl());
                oi.setItemName(req.getItemName());
                oi.setItemPrice(req.getItemPrice());
                oi.setItemQuantity(req.getItemQuantity());
                oi.setItemType(req.getItemType());
                oi.setItemUrl(req.getItemUrl());
                oi.setSellerBadge(req.getSellerBadge());
                oi.setSellerId(req.getSellerId());
                oi.setSellerName(req.getSellerName());
                oi.setOrder(order);

                items.add(oi);
        }
        try {
                orderItemsRepo.saveAll(items);
        } catch (Exception e) {
                // TODO: handle exception
                log.debug("error save order items : {}", e.getMessage());
                result = e.getMessage();
        }

        List<RelativeContact> contacts = new ArrayList<RelativeContact>();
        for(RelativeContactReq req:request.getRelativeContact()) {
                RelativeContact contact = new RelativeContact();
                contact.setRelativeName(req.getRelativeName());
                contact.setRelativePhone(req.getRelativePhone());
                contact.setRelativeRelation(RelativeRelation.valueOf(req.getRelativeRelation()));
                contact.setApplication(application);

                contacts.add(contact);
        }
        try {
                relativeContactRepo.saveAll(contacts);
        } catch (Exception e) {
                // TODO: handle exception
                log.debug("error save relative contact : {}", e.getMessage());
                result = e.getMessage();
        }
        return result;
    }
    
    public Application findByOrderId(String orderId) {
        return applicationRepo.findByOrderId(orderId);
    }
    
    public String updateDeviceReturn(Application app, List<OrderItemsReq> items, 
            String statusItem) {
        String result = "success";
        try {
            items.stream().map((data) -> {
                OrderItems item = new OrderItems();
                item = findOrderItemByApplicationAndItemId(app.getAppId(), data.getItemId());
                return item;
            }).filter((item) -> (item != null)).map((item) -> {
                item.setStatus(OrderItems.StatusItem.valueOf(statusItem));
                return item;
            }).forEachOrdered((item) -> {
                orderItemsRepo.save(item);
            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = "error save data";
            return result;
        }
    }
    
    private OrderItems findOrderItemByApplicationAndItemId(Long appId, String itemId) {
        return orderItemsRepo.findByAppIdAndOrderItem(appId, itemId);
    }
    
    public String saveApp(Application app) {
        String result = "success";
        try {
            applicationRepo.save(app);
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    } 
}
