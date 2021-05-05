package com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Model;


/*
 * @author Devendra Kharatmal
 */


public class MyModel {

    String equip_name;
    String equip_img_Url;

    public MyModel() {
    }

    public MyModel(String equip_name, String equip_img_Url) {
        this.equip_name = equip_name;
        this.equip_img_Url = equip_img_Url;
    }

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
