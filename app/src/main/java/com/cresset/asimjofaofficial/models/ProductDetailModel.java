package com.cresset.asimjofaofficial.models;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailModel {

    public String Message;
    public String Status;
    public List<ProductDetailList> ProductDetail;
    public List<String> ImagesLink;
    public List<ProductDetailSize> Size;
    public List<ProductAddons> Addons;

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

    public List<ProductDetailList> getProductDetail() {
        return ProductDetail;
    }

    public void setProductDetail(List<ProductDetailList> productDetail) {
        ProductDetail = productDetail;
    }

    public List<String> getImagesLink() {
        return ImagesLink;
    }

    public void setImagesLink(List<String> imagesLink) {
        ImagesLink = imagesLink;
    }

    public List<ProductDetailSize> getSize() {
        return Size;
    }

    public void setSize(List<ProductDetailSize> size) {
        Size = size;
    }

    public List<ProductAddons> getAddons() {
        return Addons;
    }

    public void setAddons(List<ProductAddons> addons) {
        Addons = addons;
    }
}
