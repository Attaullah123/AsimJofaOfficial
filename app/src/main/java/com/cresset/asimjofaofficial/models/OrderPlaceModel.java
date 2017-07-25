package com.cresset.asimjofaofficial.models;

/**
 * Created by cresset on 21/06/2017.
 */

public class OrderPlaceModel {
    public String ProjectId;
    public String CustomerId;
    public boolean IsShippingBillingAddressSame;
    public String PaymentMethodSystemName;
    public String ShippingMethodSystemName;
    public BillingModel BillingAddress;
    public ShippingModel ShippingAddress;

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public boolean isShippingBillingAddressSame() {
        return IsShippingBillingAddressSame;
    }

    public void setShippingBillingAddressSame(boolean shippingBillingAddressSame) {
        IsShippingBillingAddressSame = shippingBillingAddressSame;
    }

    public String getPaymentMethodSystemName() {
        return PaymentMethodSystemName;
    }

    public void setPaymentMethodSystemName(String paymentMethodSystemName) {
        PaymentMethodSystemName = paymentMethodSystemName;
    }

    public String getShippingMethodSystemName() {
        return ShippingMethodSystemName;
    }

    public void setShippingMethodSystemName(String shippingMethodSystemName) {
        ShippingMethodSystemName = shippingMethodSystemName;
    }

    public BillingModel getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(BillingModel billingAddress) {
        BillingAddress = billingAddress;
    }

    public ShippingModel getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(ShippingModel shippingAddress) {
        ShippingAddress = shippingAddress;
    }
}

