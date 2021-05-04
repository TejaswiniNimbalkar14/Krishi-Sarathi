package com.tejaswininimbalkar.krishisarathi.Owner.Model;

public class OwnerData {
    private boolean equipment_owner;
    private String userName;
    private String date_of_Birthday;
    private String no_of_Tractor;
    private String no_of_Equipment;
    private String fullName;
    private String owner_Name;
    private String password;
    private String owner_ID;    // add this field

    public OwnerData() {
    }


    public OwnerData(boolean equipment_owner, String userName, String date_of_Birthday, String no_of_Tractor, String no_of_Equipment, String password, String owner_ID) {
        this.equipment_owner = equipment_owner;
        this.userName = userName;
        this.date_of_Birthday = date_of_Birthday;
        this.no_of_Tractor = no_of_Tractor;
        this.no_of_Equipment = no_of_Equipment;
        this.password = password;
        this.owner_ID = owner_ID; // and here also
    }

    public OwnerData(String owner_Name) {
        this.owner_Name = owner_Name;
    }

    //    public OwnerData(String fullName) {
//        this.fullName = fullName;
//    }


    public String getOwner_ID() {
        return owner_ID;
    }

    public void setOwner_ID(String owner_ID) {
        this.owner_ID = owner_ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOwner_Name() {
        return owner_Name;
    }

    public void setOwner_Name(String owner_Name) {
        this.owner_Name = owner_Name;
    }

    //@PropertyName("isEquipment_owner")
    public boolean isEquipment_owner() {
        return equipment_owner;
    }


    public void setEquipment_owner(boolean equipment_owner) {
        this.equipment_owner = equipment_owner;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate_of_Birthday() {
        return date_of_Birthday;
    }

    public void setDate_of_Birthday(String date_of_Birthday) {
        date_of_Birthday = date_of_Birthday;
    }

    public String getNo_of_Tractor() {
        return no_of_Tractor;
    }

    public void setNo_of_Tractor(String no_of_Tractor) {
        this.no_of_Tractor = no_of_Tractor;
    }

    public String getNo_of_Equipment() {
        return no_of_Equipment;
    }

    public void setNo_of_Equipment(String no_of_Equipment) {
        this.no_of_Equipment = no_of_Equipment;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
