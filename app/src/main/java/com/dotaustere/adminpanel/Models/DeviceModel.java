package com.dotaustere.adminpanel.Models;

public class DeviceModel {

    String deviceID,userPhoneNumber;
    boolean valid;


    public DeviceModel() {
    }


    public DeviceModel(String deviceID, String userPhoneNumber, boolean valid) {
        this.deviceID = deviceID;
        this.userPhoneNumber = userPhoneNumber;
        this.valid = valid;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
