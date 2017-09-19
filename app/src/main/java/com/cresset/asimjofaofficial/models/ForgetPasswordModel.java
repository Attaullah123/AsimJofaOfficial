package com.cresset.asimjofaofficial.models;

/**
 * Created by attaullahkhizar on 9/19/17.
 */

public class ForgetPasswordModel {
    private String Message;
    private String Status;
    private String Password;
    private String Email;

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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}

