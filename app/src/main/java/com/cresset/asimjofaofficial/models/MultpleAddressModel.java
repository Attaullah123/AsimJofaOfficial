package com.cresset.asimjofaofficial.models;

import java.util.List;

/**
 * Created by attaullahkhizar on 10/14/17.
 */

public class MultpleAddressModel {

    String Status;
    String Message;
    public List<MultpleAddressList> CustomerAddress;

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

    public List<MultpleAddressList> getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(List<MultpleAddressList> customerAddress) {
        CustomerAddress = customerAddress;
    }
}
