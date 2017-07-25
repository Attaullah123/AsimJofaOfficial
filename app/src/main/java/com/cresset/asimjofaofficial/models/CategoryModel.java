package com.cresset.asimjofaofficial.models;

import java.util.List;



public class CategoryModel  {
    String Status;
    String Message;
    public List<CategoryList> Parentlist;

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

    public List<CategoryList> getParentlist() {
        return Parentlist;
    }

    public void setParentlist(List<CategoryList> parentlist) {
        Parentlist = parentlist;
    }
}
