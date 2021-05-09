package com.tejaswininimbalkar.krishisarathi.Owner.Model;

public class AcceptReq_model {
    String Booking_Id;
    String Equipment_name;
    String Working_Date;
    String Working_Time;
    String Requester_Id;
    String Owner_ID;

    public AcceptReq_model() {
    }

    public AcceptReq_model(String booking_Id, String equipment_name, String working_Date, String working_Time, String requester_Id, String owner_ID) {
        Booking_Id = booking_Id;
        Equipment_name = equipment_name;
        Working_Date = working_Date;
        Working_Time = working_Time;
        Requester_Id = requester_Id;
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

    public String getRequester_Id() {
        return Requester_Id;
    }

    public void setRequester_Id(String requester_Id) {
        Requester_Id = requester_Id;
    }

    public String getOwner_ID() {
        return Owner_ID;
    }

    public void setOwner_ID(String owner_ID) {
        Owner_ID = owner_ID;
    }
}
