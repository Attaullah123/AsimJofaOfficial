package com.cresset.asimjofaofficial.models;

/**
 * Created by Attaullah Khizar on 20/06/2017.
 */

public class UserModel {

/*
    public String FirstName;
    public String LastName;
    public String Day;
    public String month;
    public String Year;
    public String Email;
    public String Password;
    public String IpAddress;
*/

    public String UserID;
    public String UserName;
    public String Email;
    public boolean IsGuest;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public boolean isGuest() {
        return IsGuest;
    }

    public void setGuest(boolean guest) {
        IsGuest = guest;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
