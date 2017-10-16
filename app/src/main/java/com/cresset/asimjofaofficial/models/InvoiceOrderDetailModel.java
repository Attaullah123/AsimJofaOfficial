package com.cresset.asimjofaofficial.models;

import java.util.List;

/**
 * Created by attaullahkhizar on 10/14/17.
 */

public class InvoiceOrderDetailModel {
    public String OrderTotal;
    public String Id;
    public InvoiceBillingAddress BillingAddress;
    public InvoiceShippingAddress ShippingAddress;
    private List<InvoiceCartItem> OrderItems;

    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public InvoiceBillingAddress getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(InvoiceBillingAddress billingAddress) {
        BillingAddress = billingAddress;
    }

    public InvoiceShippingAddress getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(InvoiceShippingAddress shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public List<InvoiceCartItem> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<InvoiceCartItem> orderItems) {
        OrderItems = orderItems;
    }
}
