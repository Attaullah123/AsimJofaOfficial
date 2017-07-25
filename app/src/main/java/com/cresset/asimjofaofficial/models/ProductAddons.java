package com.cresset.asimjofaofficial.models;

/**
 * Created by cresset on 27/05/2017.
 */

public class ProductAddons {
    public String ProductMappingAttributeId;
    public String AddonsName;
    public String AddonsPrice;
    public String ProductAttributeValueId;
    boolean selected = false;

    public String getProductMappingAttributeId() {
        return ProductMappingAttributeId;
    }

    public void setProductMappingAttributeId(String productMappingAttributeId) {
        ProductMappingAttributeId = productMappingAttributeId;
    }

    public String getAddonsName() {
        return AddonsName;
    }

    public void setAddonsName(String addonsName) {
        AddonsName = addonsName;
    }

    public String getAddonsPrice() {
        return AddonsPrice;
    }

    public void setAddonsPrice(String addonsPrice) {
        AddonsPrice = addonsPrice;
    }

    public String getProductAttributeValueId() {
        return ProductAttributeValueId;
    }

    public void setProductAttributeValueId(String productAttributeValueId) {
        ProductAttributeValueId = productAttributeValueId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
