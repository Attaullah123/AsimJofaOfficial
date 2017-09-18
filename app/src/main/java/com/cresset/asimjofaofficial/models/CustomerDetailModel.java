package com.cresset.asimjofaofficial.models;

/**
 * Created by attaullahkhizar on 9/18/17.
 */

public class  CustomerDetailModel {
    private int Id;
    private String UserName;
    private String Email;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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
