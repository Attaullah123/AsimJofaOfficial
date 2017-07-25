package com.cresset.asimjofaofficial.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cresset on 06/06/2017.
 */

public class AddToCartModel {
    public String ProjectId;
    public String ProductId;
    public String Quantity;
    public String CustomerId;

   public List<AttributesItem> Attribute;

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public List<AttributesItem> getAttribute() {
        return Attribute;
    }

    public void setAttribute(List<AttributesItem> attribute) {
        Attribute = attribute;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

}
