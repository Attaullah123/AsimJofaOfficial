package com.cresset.asimjofaofficial.models;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailList {
    public int Id;
    public String Sku;
    public String Name;
    public float Price;
    public float OldPrice;
    public String ShortDescription;
    public String FullDescription;
    public boolean IsNewProduct;
    public boolean IsOutOfStock;
    public boolean CallForPrice;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSku() {
        return Sku;
    }

    public void setSku(String sku) {
        Sku = sku;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public float getOldPrice() {
        return OldPrice;
    }

    public void setOldPrice(float oldPrice) {
        OldPrice = oldPrice;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getFullDescription() {
        return FullDescription;
    }

    public void setFullDescription(String fullDescription) {
        FullDescription = fullDescription;
    }

    public boolean isNewProduct() {
        return IsNewProduct;
    }

    public void setNewProduct(boolean newProduct) {
        IsNewProduct = newProduct;
    }

    public boolean isOutOfStock() {
        return IsOutOfStock;
    }

    public void setOutOfStock(boolean outOfStock) {
        IsOutOfStock = outOfStock;
    }

    public boolean isCallForPrice() {
        return CallForPrice;
    }

    public void setCallForPrice(boolean callForPrice) {
        CallForPrice = callForPrice;
    }
}
