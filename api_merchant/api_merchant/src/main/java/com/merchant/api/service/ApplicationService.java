package com.merchant.api.service;

import com.google.gson.Gson;
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
import com.merchant.api.model.payload.response.ErrorResponse;
import com.merchant.api.model.payload.response.HistoryResponse;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

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

    public String saveAll(ApplicationReq request, String action) {
        try {
            String result = "Success";
            Application application = new Application();
            application.setConsentTimeStamp(new Date());
            application.setExpiredTime(new Date(new Date().getTime() + 86400000));
            application.setOrderId(("ORDER" + new SimpleDateFormat("MMYY").format(new Date())
                    + ThreadLocalRandom.current().nextInt()).replace("-", ""));
            application.setStatus(Status.NEW_REQUEST);
            applicationRepo.save(application);
            // set param untuk request ke api empower submission
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> empowerParam = new HashMap<>();
            empowerParam.put("orderId", application.getOrderId());
            empowerParam.put("expiredTime", sdf.format(application.getExpiredTime()));
            empowerParam.put("consentTimeStamp", sdf.format(application.getConsentTimeStamp()));

            AdditionalInfo additionalInfo = new AdditionalInfo();
            additionalInfo.setCustomerRegistrationTime(new Date(new Date().getTime() - (158 * 60 * 60 * 1000)));
            additionalInfo.setNumberOfTransaction(10);
            additionalInfo.setOfferCode(request.getAdditionalInfo().getOfferCode());
            additionalInfo.setOtpVerificationTime(new Date(additionalInfo.getCustomerRegistrationTime().getTime() + 86400000));
            additionalInfo.setVolumeOfTransaction(new Double(10000000));
            additionalInfo.setUserId(new Long(194));
            additionalInfo.setApplication(application);
            additionalInfoRepo.save(additionalInfo);
            // set param untuk request ke api empower submission
            Map<String, Object> empowerAddInfo = new HashMap<>();
            empowerAddInfo.put("offerCode", additionalInfo.getOfferCode());
            empowerAddInfo.put("userId", additionalInfo.getUserId());
            empowerAddInfo.put("otpVerifiedTime", sdf.format(additionalInfo.getOtpVerificationTime()));
            empowerAddInfo.put("customerRegistrationTime", sdf.format(additionalInfo.getCustomerRegistrationTime()));
            empowerAddInfo.put("numberOfTransaction", additionalInfo.getNumberOfTransaction());
            empowerAddInfo.put("volumeOfTransaction", additionalInfo.getVolumeOfTransaction().intValue());
            empowerParam.put("additionalInfo", empowerAddInfo);

            BankInfo bankInfo = new BankInfo();
            bankInfo.setBankCode(request.getBankInfo().getBankCode());
            bankInfo.setBankName(request.getBankInfo().getBankName());
            bankInfo.setAccountNumber(request.getBankInfo().getAccountNumber());
            bankInfo.setAccountName(request.getBankInfo().getAccountName());
            bankInfo.setApplication(application);
            bankInfoRepo.save(bankInfo);
            empowerParam.put("bankInfo", request.getBankInfo());

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
            customerInfoRepo.save(customerInfo);
            Map<String, Object> empowerCustInfo = new HashMap<>();
            empowerCustInfo.put("emailAddress", customerInfo.getEmailAddress());
            empowerCustInfo.put("phoneNumber", customerInfo.getPhoneNumber());
            empowerCustInfo.put("ktpNumber", customerInfo.getKtpNumber());
            empowerCustInfo.put("firstName", customerInfo.getFirstName());
            empowerCustInfo.put("lastName", customerInfo.getLastName());
            empowerCustInfo.put("birthplaceCode", customerInfo.getBirthplaceCode());
            empowerCustInfo.put("dateOfBirth", sdf.format(customerInfo.getDateOfBirth()));
            empowerCustInfo.put("genderType", customerInfo.getGenderType());
            empowerCustInfo.put("motherName", customerInfo.getMotherName());
            empowerCustInfo.put("lastEducation", customerInfo.getLastEducation());
            empowerCustInfo.put("maritalStatus", customerInfo.getMaritalStatus());
            empowerCustInfo.put("dependentNumber", customerInfo.getDependentNumber());
            empowerCustInfo.put("ektpFlag", customerInfo.getEktpFlag() ? 1:0);

            List<CustomerAddress> listCustAddress = new ArrayList<>();
            List<Object> paramCustAdd = new ArrayList<>();
            request.getCustomerInfo().getCustomerAddress().forEach(address -> {
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
                paramCustAdd.add(address);
            });
            customerAddressRepo.saveAll(listCustAddress);
            empowerCustInfo.put("customerAddress", paramCustAdd);
            empowerParam.put("customerInfo", empowerCustInfo);

            List<DocumentPhoto> documentPhotos = new ArrayList<>();
            List<Object> paramDocPhoto = new ArrayList<>();
            request.getDocumentPhoto().forEach(req -> {
                DocumentPhoto photo = new DocumentPhoto();
                photo.setDocumentType(DocumentType.valueOf(req.getDocumentType()));
                photo.setDocumentUrl(req.getDocumentUrl());
                photo.setApplication(application);

                documentPhotos.add(photo);
                paramDocPhoto.add(req);
            });
            documentPhotoRepo.saveAll(documentPhotos);
            empowerParam.put("documentPhoto", paramDocPhoto);

            EmploymentInfo employmentInfo = new EmploymentInfo();
            employmentInfo.setEmploymentType(EmploymentType.valueOf(request.getEmploymentInfo().getEmploymentType()));
            employmentInfo.setIndustryType(IndustryType.valueOf(request.getEmploymentInfo().getIndustryType()));
            employmentInfo.setLengthOfEmployment(request.getEmploymentInfo().getLengthOfEmployment());
            employmentInfo.setMonthlyIncome((double) request.getEmploymentInfo().getMonthlyIncome());
            employmentInfo.setOtherInstallment((double) request.getEmploymentInfo().getOtherInstallment());
            employmentInfo.setProfession(Profession.valueOf(request.getEmploymentInfo().getProfession()));
            employmentInfo.setCompanyName(request.getEmploymentInfo().getCompanyName());
            employmentInfo.setApplication(application);
            employmentInfoRepo.save(employmentInfo);
            empowerParam.put("employmentInfo", request.getEmploymentInfo());

            Installment installment = new Installment();
            installment.setMonthInstallment(request.getInstallment().getMonthInstallment());
            installment.setAmount((double) request.getInstallment().getAmount());
            installment.setApplication(application);
            installmentRepo.save(installment);
            empowerParam.put("installment", request.getInstallment());

            Orders order = new Orders();
            order.setInvoiceNumber(("INV" + new SimpleDateFormat("MMYY").format(new Date())
                    + ThreadLocalRandom.current().nextInt()).replace("-", ""));
            order.setShippingAddress(request.getOrder().getShippingAddress());
            order.setTotalPrice((double) request.getOrder().getTotalPrice());
            order.setApplication(application);
            orderRepo.save(order);
            Map<String, Object> empowerOrder = new HashMap<>();
            empowerOrder.put("invoiceNumber", order.getInvoiceNumber());
            empowerOrder.put("shippingAddress", order.getShippingAddress());
            empowerOrder.put("totalPrice", order.getTotalPrice().intValue());

            List<OrderItems> items = new ArrayList<>();
            List<Object> paramItems = new ArrayList<>();
            request.getOrder().getItems().forEach((req) -> {
                OrderItems oi = new OrderItems();
                oi.setItemId(req.getItemId());
                oi.setItemImageUrl(req.getItemImageUrl());
                oi.setItemName(req.getItemName());
                oi.setItemPrice((double) req.getItemPrice());
                oi.setItemQuantity(req.getItemQuantity());
                oi.setItemType(req.getItemType());
                oi.setItemUrl(req.getItemUrl());
                oi.setSellerBadge(req.getSellerBadge());
                oi.setSellerId(req.getSellerId());
                oi.setSellerName(req.getSellerName());
                oi.setOrder(order);

                items.add(oi);
                paramItems.add(req);
            });
            orderItemsRepo.saveAll(items);
            empowerOrder.put("items", paramItems);
            empowerParam.put("order", empowerOrder);

            List<RelativeContact> contacts = new ArrayList<>();
            List<Object> paramContacts = new ArrayList<>();
            request.getRelativeContact().forEach((req) -> {
                RelativeContact contact = new RelativeContact();
                contact.setRelativeName(req.getRelativeName());
                contact.setRelativePhone(req.getRelativePhone());
                contact.setRelativeRelation(RelativeRelation.valueOf(req.getRelativeRelation()));
                contact.setApplication(application);

                contacts.add(contact);
                paramContacts.add(req);
            });
            relativeContactRepo.saveAll(contacts);
            empowerParam.put("relativeContact", paramContacts);
            log.debug("param empower : {}", new Gson().toJson(empowerParam));
            if(action.equalsIgnoreCase("NEW_DATA")) {
                // hit api empower application submission
                EmpowerApiService apiService = new EmpowerApiService();
                String token = apiService.getToken("empower", "empower123", 
                        "http://localhost:8083/api/v1/authenticate");
                ResponseEntity res = apiService
                        .excute("http://localhost:8083/api/v1/applications", new Gson().toJson(empowerParam), 
                                HttpMethod.POST, String.class, new ErrorResponse(), token);
                log.debug("response empower : {}", res);
            }

            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Application findByOrderId(String orderId) {
        return applicationRepo.findByOrderId(orderId);
    }

    public String updateDeviceReturn(Application app, List<OrderItemsReq> items,
            String statusItem) {
        String result = "success";
        try {
            app.setStatus(Status.RETURN);
            applicationRepo.save(app);
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

    public List<CustomerInfo> getCustByNoKtp(String noKtp, String noHp) {
        return customerInfoRepo.getByNoKtpAndNoHp(noKtp, noHp);
    }

    public HistoryResponse getHistory(CustomerInfo customerInfo) {
        List<OrderItems> itemses = orderItemsRepo.getHistoryByNoKtpAndNoHp(
                customerInfo.getKtpNumber(), customerInfo.getPhoneNumber());
        List<OrderItemsReq> itemsReqs = new ArrayList<>();
        itemses.forEach(x -> {
            OrderItemsReq itemsReq = new OrderItemsReq();
            itemsReq.setItemId(x.getItemId());
            itemsReq.setItemImageUrl(x.getItemImageUrl());
            itemsReq.setItemName(x.getItemName());
            itemsReq.setItemPrice(x.getItemPrice().intValue());
            itemsReq.setItemQuantity(x.getItemQuantity());
            itemsReq.setItemType(x.getItemType());
            itemsReq.setItemUrl(x.getItemUrl());
            itemsReq.setSellerBadge(x.getSellerBadge());
            itemsReq.setSellerId(x.getSellerId());
            itemsReq.setSellerName(x.getSellerName());
            itemsReqs.add(itemsReq);
        });
        HistoryResponse response = new HistoryResponse(customerInfo.getFirstName()
                + " " + customerInfo.getLastName(),
                "GOLD", itemsReqs);
        return response;
    }
}
