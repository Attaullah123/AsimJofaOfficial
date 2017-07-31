package com.cresset.asimjofaofficial.models;

import java.util.List;


public class CurrencyModel {
    public String Message;
    public String Status;
    public List<CurrencyListModel> CurrencyList;


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

    public List<CurrencyListModel> getCurrencyList() {
        return CurrencyList;
    }

    public void setCurrencyList(List<CurrencyListModel> currencyList) {
        CurrencyList = currencyList;
    }


}
