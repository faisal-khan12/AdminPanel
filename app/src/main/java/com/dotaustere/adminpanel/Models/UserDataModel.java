package com.dotaustere.adminpanel.Models;

public class UserDataModel {

    String uuID, mobileNumber, userName, emailAddress, fullName, gender, dateOfBirth, razorPaymentID, userToken;

    boolean verification;

    public UserDataModel() {
    }


    public UserDataModel(String uuID, String mobileNumber, String userName, String emailAddress, String fullName, String gender, String dateOfBirth, String razorPaymentID, String userToken, boolean verification) {
        this.uuID = uuID;
        this.mobileNumber = mobileNumber;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.razorPaymentID = razorPaymentID;
        this.userToken = userToken;
        this.verification = verification;
    }

    public String getUuID() {
        return uuID;
    }

    public void setUuID(String uuID) {
        this.uuID = uuID;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRazorPaymentID() {
        return razorPaymentID;
    }

    public void setRazorPaymentID(String razorPaymentID) {
        this.razorPaymentID = razorPaymentID;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public boolean isVerification() {
        return verification;
    }

    public void setVerification(boolean verification) {
        this.verification = verification;
    }
}
