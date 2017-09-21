package com.cresset.asimjofaofficial.models;


public class ProductListModel {
    public String Id;
    public String Sku;
    public String Name;
    public String Price;
    public String OldPrice;
    public String ImageLink;
    public boolean IsNewProduct;
    public boolean IsOutOfStock;
    private boolean CallForPrice;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getOldPrice() {
        return OldPrice;
    }

    public void setOldPrice(String oldPrice) {
        OldPrice = oldPrice;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
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
