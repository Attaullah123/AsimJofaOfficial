package com.cresset.asimjofaofficial.models;

/**
 * Created by cresset on 17/06/2017.
 */

public class ShippingModel {
    public String FullName;
    public String Email;
    public String CountryId;
    public String StateProvinceId;
    public String City;
    public String Address1;
    public String ZipPostalCode;
    public String PhoneNumber;

    public String birthdayDay;
    public String birthdayMonth;
    public String birthdayYear;


    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCountryId() {
        return CountryId;
    }

    public void setCountryId(String countryId) {
        CountryId = countryId;
    }

    public String getStateProvinceId() {
        return StateProvinceId;
    }

    public void setStateProvinceId(String stateProvinceId) {
        StateProvinceId = stateProvinceId;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getZipPostalCode() {
        return ZipPostalCode;
    }

    public void setZipPostalCode(String zipPostalCode) {
        ZipPostalCode = zipPostalCode;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getBirthdayDay() {
        return birthdayDay;
    }

    public void setBirthdayDay(String birthdayDay) {
        this.birthdayDay = birthdayDay;
    }

    public String getBirthdayMonth() {
        return birthdayMonth;
    }

    public void setBirthdayMonth(String birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }

    public String getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(String birthdayYear) {
        this.birthdayYear = birthdayYear;
    }
}
