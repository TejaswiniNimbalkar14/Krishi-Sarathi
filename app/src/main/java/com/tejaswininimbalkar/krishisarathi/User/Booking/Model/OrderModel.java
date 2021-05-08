package com.tejaswininimbalkar.krishisarathi.User.Booking.Model;
/*
 * @author Devendra Kharatmal
 */
public class OrderModel {
    String order_Id,equip_Id,user_Id;

    public OrderModel() {

    }

    public OrderModel(String order_Id, String equip_Id, String user_Id) {
        this.order_Id = order_Id;
        this.equip_Id = equip_Id;
        this.user_Id = user_Id;
    }

    public String getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(String order_Id) {
        this.order_Id = order_Id;
    }

    public String getEquip_Id() {
        return equip_Id;
    }

    public void setEquip_Id(String equip_Id) {
        this.equip_Id = equip_Id;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }
}
