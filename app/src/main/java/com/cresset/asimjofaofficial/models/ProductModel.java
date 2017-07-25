package com.cresset.asimjofaofficial.models;


import java.util.List;

public class ProductModel {

    private String Message;
    private String Status;
    private List<ProductListModel> ProductList;

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

    public List<ProductListModel> getProductList() {
        return ProductList;
    }

    public void setProductList(List<ProductListModel> productList) {
        ProductList = productList;
    }
}
