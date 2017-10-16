package com.cresset.asimjofaofficial.models;

import java.util.List;

/**
 * Created by attaullahkhizar on 9/21/17.
 */

public class BillingShippingModel {

    private String Message;
    private String Status;
    private List<CustomerAddressModel> CustomerAddress;

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

    public List<CustomerAddressModel> getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(List<CustomerAddressModel> customerAddress) {
        CustomerAddress = customerAddress;
    }
}
