package com.tejaswininimbalkar.krishisarathi.Booking.Model;

public class MyModel {

    String equipment_name;
    String equip_img_Url;

    public MyModel() {
    }

    public MyModel(String equipment_name, String equip_img_Url) {
        this.equipment_name = equipment_name;
        this.equip_img_Url = equip_img_Url;
    }

    public String getEquipment_name() {
        return equipment_name;
    }

    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public String getEquip_img_Url() {
        return equip_img_Url;
    }

    public void setEquip_img_Url(String equip_img_Url) {
        this.equip_img_Url = equip_img_Url;
    }
}
