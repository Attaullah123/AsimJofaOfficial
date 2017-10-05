package com.cresset.asimjofaofficial.models;

/**
 * Created by mehboob.khan on 10/4/2017.
 */

public class UpdateBillingAndShippingModel {
    public String ProjectId;
    public BillingShippingDetailModel ShippingAddress;
    public BillingShippingDetailModel BillingAddress;

    public String getProjectID() {
        return ProjectId;
    }

    public void setProjectID(String projectID) {
        ProjectId = projectID;
    }

    public BillingShippingDetailModel getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(BillingShippingDetailModel shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public BillingShippingDetailModel getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(BillingShippingDetailModel billingAddress) {
        BillingAddress = billingAddress;
    }
}
