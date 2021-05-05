package com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Model;

public class OwnerModel {

    String userName,owner_ID;

    public OwnerModel() {
    }

    public OwnerModel(String userName, String owner_ID) {
        this.userName = userName;
        this.owner_ID = owner_ID;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOwner_ID() {
        return owner_ID;
    }

    public void setOwner_ID(String owner_ID) {
        this.owner_ID = owner_ID;
    }
}
