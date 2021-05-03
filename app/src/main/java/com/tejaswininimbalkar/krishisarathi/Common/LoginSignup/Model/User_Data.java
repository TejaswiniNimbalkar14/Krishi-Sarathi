//Jayesh pravin borase
package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Model;

public class User_Data {

    private String profile_img;
    private String first_name;
    private String last_name;
    private String fullName;
    private String email_id;
    private String password;
    private String phone_num;
    private String gender;
    private String address;
    private boolean equipment_owner;

    public User_Data(String fullName, String email_id, String password, String phone_num, String gender, boolean Equipment_owner) {
        this.fullName = fullName;
        this.email_id = email_id;
        this.password = password;
        this.phone_num = phone_num;
        this.gender = gender;
        this.equipment_owner = Equipment_owner;
    }

    public User_Data(String fullName, String email_id, String phone_num, String gender, boolean Equipment_owner, String profile_img) {
        this.profile_img = profile_img;
        this.fullName = fullName;
        this.email_id = email_id;
        this.phone_num = phone_num;
        this.gender = gender;
        this.equipment_owner = Equipment_owner;
    }

    public User_Data() {
    }

    public User_Data(String fullName, String email_id) {
        this.fullName = fullName;
        this.email_id = email_id;
    }

    public User_Data(String profile_img, String first_name, String last_name, String email_id, String password, String phone_num, String address) {
        this.profile_img = profile_img;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
        this.password = password;
        this.phone_num = phone_num;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isEquipment_owner() {
        return equipment_owner;
    }

    public void setEquipment_owner(boolean equipment_owner) {
        this.equipment_owner = equipment_owner;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
