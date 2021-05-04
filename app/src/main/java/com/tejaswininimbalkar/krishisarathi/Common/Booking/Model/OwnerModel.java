package com.tejaswininimbalkar.krishisarathi.Common.Booking.Model;
/*
 * @author Devendra Kharatmal
 */
public class OwnerModel {


    String userName,owner_ID,equipment_Name;

    public OwnerModel() {
    }

    public OwnerModel(String userName, String owner_ID, String equipment_Name) {
        this.userName = userName;
        this.owner_ID = owner_ID;
        this.equipment_Name = equipment_Name;
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

    public String getEquipment_Name() {
        return equipment_Name;
    }

    public void setEquipment_Name(String equipment_Name) {
        this.equipment_Name = equipment_Name;
    }
}

