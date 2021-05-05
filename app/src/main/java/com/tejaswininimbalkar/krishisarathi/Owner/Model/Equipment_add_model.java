package com.tejaswininimbalkar.krishisarathi.Owner.Model;

public class Equipment_add_model {
    private String equip_name;
    private String equip_img_Url;


    public Equipment_add_model() {
    }

    public Equipment_add_model(String equip_name, String equip_img_Url) {
        this.equip_name = equip_name;
        this.equip_img_Url = equip_img_Url;
    }

    //here get method
    public String getEquip_name() {
        return equip_name;
    }

    public void setEquip_name(String equip_name) {
        this.equip_name = equip_name;

    }

    public String getEquip_img_Url() {
        return equip_img_Url;
    }

    public void setEquip_img_Url(String equip_img_Url) {
        this.equip_img_Url = equip_img_Url;
    }
}
