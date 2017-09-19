package com.cresset.asimjofaofficial.models;



public class ShippingmethodList {
    public String Id;
    public String Name;
    public String ShippingRateComputationMethodSystemName;
    public String Description;
    public float Price;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getShippingRateComputationMethodSystemName() {
        return ShippingRateComputationMethodSystemName;
    }

    public void setShippingRateComputationMethodSystemName(String shippingRateComputationMethodSystemName) {
        ShippingRateComputationMethodSystemName = shippingRateComputationMethodSystemName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }
}
