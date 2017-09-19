package com.cresset.asimjofaofficial.models;

/**
 * Created by attaullahkhizar on 9/19/17.
 */

public class ChnagePasswordModel {
    private String Email;
    private String oldPassword;
    private String newPassword;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
