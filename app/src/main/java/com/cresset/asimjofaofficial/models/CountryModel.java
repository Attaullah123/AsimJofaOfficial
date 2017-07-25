package com.cresset.asimjofaofficial.models;

import java.util.List;


public class CountryModel {
    String Status;
    String Message;
    public List<CountryList> CountryList;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<CountryList> getCountryList() {
        return CountryList;
    }

    public void setCountryList(List<CountryList> countryList) {
        CountryList = countryList;
    }
}
