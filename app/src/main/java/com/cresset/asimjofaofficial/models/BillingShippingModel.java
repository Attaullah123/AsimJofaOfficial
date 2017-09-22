package com.cresset.asimjofaofficial.models;

/**
 * Created by attaullahkhizar on 9/21/17.
 */

public class BillingShippingModel {

    private String Message;
    private String Status;
    private CustomerAddressModel CustomerAddress;

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

    public CustomerAddressModel getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(CustomerAddressModel customerAddress) {
        CustomerAddress = customerAddress;
    }
}
