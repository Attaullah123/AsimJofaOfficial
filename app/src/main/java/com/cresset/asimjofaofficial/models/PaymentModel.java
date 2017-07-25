package com.cresset.asimjofaofficial.models;

import java.util.List;

/**
 * Created by cresset on 20/06/2017.
 */

public class PaymentModel {
    public String Message;
    public String Status;

    public List<PaymentMethodModel> PaymentMethod;

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

    public List<PaymentMethodModel> getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(List<PaymentMethodModel> paymentMethod) {
        PaymentMethod = paymentMethod;
    }
}
