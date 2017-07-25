package com.cresset.asimjofaofficial.models;

import java.util.ArrayList;



public class ProductDetailSize {
    public String ProductMappingAttributeId;
    public String SizeName;
    public String ProductAttributeValueId;

    public String getSizeName() {
        return SizeName;
    }

    public void setSizeName(String sizeName) {
        SizeName = sizeName;
    }

    public String getProductMappingAttributeId() {
        return ProductMappingAttributeId;
    }

    public void setProductMappingAttributeId(String productMappingAttributeId) {
        ProductMappingAttributeId = productMappingAttributeId;
    }



    public String getProductAttributeValueId() {
        return ProductAttributeValueId;
    }

    public void setProductAttributeValueId(String productAttributeValueId) {
        ProductAttributeValueId = productAttributeValueId;
    }
}
