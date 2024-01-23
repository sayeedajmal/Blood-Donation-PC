package com.strong.blooddashboard.model;

import java.time.LocalDateTime;

public class Donor {
    private Integer donorId;
    private String firstName;
    private String lastName;
    private String dob;
    private String contactNumber;
    private String email;
    private String bloodGroup;
    private LocalDateTime lastDonationDate;

    public Donor() {
    }

    public Integer getDonorId() {
        return donorId;
    }

    public void setDonorId(Integer donorId) {
        this.donorId = donorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String frist) {
        this.firstName = frist;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDateTime getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(LocalDateTime lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }
}
