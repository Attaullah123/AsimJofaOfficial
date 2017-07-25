package com.cresset.asimjofaofficial.models;

/**
 * Created by Attaullah Khizar on 05/07/2017.
 */

public class CustomerDetailResponse {
    String Status;
    String Message;
    CustomerDetailResponseModel CustomerDetail;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public CustomerDetailResponseModel getCustomerDetail() {
        return CustomerDetail;
    }

    public void setCustomerDetail(CustomerDetailResponseModel customerDetail) {
        CustomerDetail = customerDetail;
    }
}
