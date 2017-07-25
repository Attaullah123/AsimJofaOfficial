package com.cresset.asimjofaofficial.models;

import java.util.List;


public class GuestOrLoginResponseModel {
    String Status;
    String Message;
    String CustomerId;

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

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }
}
