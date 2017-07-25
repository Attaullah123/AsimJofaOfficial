package com.cresset.asimjofaofficial.models;

import java.util.List;

/**
 * Created by cresset on 19/06/2017.
 */

public class ShippingMethodModel {
    public String Message;
    public String Status;

    public ShippingmethodList Shippingmethod;

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

    public ShippingmethodList getShippingmethod() {
        return Shippingmethod;
    }

    public void setShippingmethod(ShippingmethodList shippingmethod) {
        Shippingmethod = shippingmethod;
    }
}
