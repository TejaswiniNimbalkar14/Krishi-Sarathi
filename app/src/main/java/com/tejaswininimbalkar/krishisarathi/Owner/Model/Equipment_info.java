package com.tejaswininimbalkar.krishisarathi.Owner.Model;

public class Equipment_info {
    private String equipment_name;
    private String equipment_company_name;
    private String model_year;

    public Equipment_info() {
    }

    public Equipment_info(String equipment_name, String equipment_company_name, String model_year) {
        this.equipment_name = equipment_name;
        this.equipment_company_name = equipment_company_name;
        this.model_year = model_year;
    }

    public Equipment_info(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public String getEquipment_name() {
        return equipment_name;
    }

    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public String getEquipment_company_name() {
        return equipment_company_name;
    }

    public void setEquipment_company_name(String equipment_company_name) {
        this.equipment_company_name = equipment_company_name;
    }

    public String getModel_year() {
        return model_year;
    }

    public void setModel_year(String model_year) {
        this.model_year = model_year;
    }
}
