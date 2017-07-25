package com.cresset.asimjofaofficial.models;

/**
 * Created by Attaullah Khizar on 05/07/2017.
 */

public class CustomerDetailResponseModel {
    public String Id;
    public String UserName;
    public String Email;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
