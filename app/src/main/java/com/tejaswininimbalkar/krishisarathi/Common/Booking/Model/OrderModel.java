package com.tejaswininimbalkar.krishisarathi.Common.Booking.Model;

public class OrderModel {

    String pname, paddress, pphone;
    String order_name;

    public OrderModel(String order_name, String pname, String paddress, String pphone) {
        this.order_name = order_name;
        this.pname = pname;
        this.paddress = paddress;
        this.pphone = pphone;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public String getPphone() {
        return pphone;
    }

    public void setPphone(String pphone) {
        this.pphone = pphone;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }
}
