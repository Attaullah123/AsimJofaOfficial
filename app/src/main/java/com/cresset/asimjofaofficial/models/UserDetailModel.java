package com.cresset.asimjofaofficial.models;

/**
 * Created by attaullahkhizar on 9/18/17.
 */

public class UserDetailModel {
    private String Message;
    private String Status;

    private CustomerDetailModel CustomerDetail;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public CustomerDetailModel getCustomerDetail() {
        return CustomerDetail;
    }

    public void setCustomerDetail(CustomerDetailModel customerDetail) {
        CustomerDetail = customerDetail;
    }
}
