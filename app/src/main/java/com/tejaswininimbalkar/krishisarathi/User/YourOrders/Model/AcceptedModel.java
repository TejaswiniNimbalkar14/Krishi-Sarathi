package com.tejaswininimbalkar.krishisarathi.User.YourOrders.Model;

public class AcceptedModel {

    String Booking_Id,Equipment_name,Requester_Id,Working_Date,Working_Time,Owner_ID;

    public AcceptedModel() {
    }

    public AcceptedModel(String booking_Id, String equipment_name, String requester_Id, String working_Date, String working_Time, String owner_ID) {
        Booking_Id = booking_Id;
        Equipment_name = equipment_name;
        Requester_Id = requester_Id;
        Working_Date = working_Date;
        Working_Time = working_Time;
        Owner_ID = owner_ID;
    }

    public String getBooking_Id() {
        return Booking_Id;
    }

    public void setBooking_Id(String booking_Id) {
        Booking_Id = booking_Id;
    }

    public String getEquipment_name() {
        return Equipment_name;
    }

    public void setEquipment_name(String equipment_name) {
        Equipment_name = equipment_name;
    }

    public String getRequester_Id() {
        return Requester_Id;
    }

    public void setRequester_Id(String requester_Id) {
        Requester_Id = requester_Id;
    }

    public String getWorking_Date() {
        return Working_Date;
    }

    public void setWorking_Date(String working_Date) {
        Working_Date = working_Date;
    }

    public String getWorking_Time() {
        return Working_Time;
    }

    public void setWorking_Time(String working_Time) {
        Working_Time = working_Time;
    }

    public String getOwner_ID() {
        return Owner_ID;
    }

    public void setOwner_ID(String owner_ID) {
        Owner_ID = owner_ID;
    }
}
