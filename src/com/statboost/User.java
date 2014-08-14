package com.statboost;

import javax.persistence.*;

@Entity
@Table(name="usr_uid")
public class User {
    @Id @GeneratedValue
    @Column(name="usr_uid")
    private int uid;
    @Column(name="usr_role")
    private String role;
    @Column(name="usr_email")
    private String email;
    @Column(name="usr_password")
    private String password;
    @Column(name="usr_address_1")
    private String address1;
    @Column(name="usr_address_2")
    private String address2;
    @Column(name="usr_city")
    private String city;
    @Column(name="usr_state")
    private String state;
    @Column(name="usr_zip")
    private String zip;
    @Column(name="usr_first_name")
    private String firstName;
    @Column(name="usr_last_name")
    private String lastName;
    @Column(name="usr_phone")
    private String phone;
    @Column(name="usr_newsletter")
    private boolean newsletter;
    @Column(name="usr_active")
    private boolean active;
    @Column(name="usr_dci_number")
    private String dciNumber;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDciNumber() {
        return dciNumber;
    }

    public void setDciNumber(String dciNumber) {
        this.dciNumber = dciNumber;
    }
}
