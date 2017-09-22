package com.cresset.asimjofaofficial.models;

/**
 * Created by cresset on 04/07/2017.
 */

public class CurrencyListModel {
    public int Id;
    public String Name;
    public String CurrencyCode;
    public Float Rate;
    public int DisplayOrder;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }


    public Float getRate() {
        return Rate;
    }

    public void setRate(Float rate) {
        Rate = rate;
    }

    public int getDisplayOrder() {
        return DisplayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        DisplayOrder = displayOrder;
    }

}
