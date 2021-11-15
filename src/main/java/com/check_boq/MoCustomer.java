package com.check_boq;

import javafx.collections.ObservableArray;

public class MoCustomer  {
    int CS_ID ;
    String CS_Name ;
    String CS_Phone ;
    String CS_Email ;

    public MoCustomer() {
    }

    public MoCustomer(String CS_Name, String CS_Phone, String CS_Email) {
        this.CS_Name = CS_Name;
        this.CS_Phone = CS_Phone;
        this.CS_Email = CS_Email;
    }

    public MoCustomer(int CS_ID, String CS_Name, String CS_Phone, String CS_Email) {
        this.CS_ID = CS_ID;
        this.CS_Name = CS_Name;
        this.CS_Phone = CS_Phone;
        this.CS_Email = CS_Email;
    }

    public int getCS_ID() {
        return CS_ID;
    }

    public void setCS_ID(int CS_ID) {
        this.CS_ID = CS_ID;
    }

    public String getCS_Name() {
        return CS_Name;
    }

    public void setCS_Name(String CS_Name) {
        this.CS_Name = CS_Name;
    }

    public String getCS_Phone() {
        return CS_Phone;
    }

    public void setCS_Phone(String CS_Phone) {
        this.CS_Phone = CS_Phone;
    }

    public String getCS_Email() {
        return CS_Email;
    }

    public void setCS_Email(String CS_Email) {
        this.CS_Email = CS_Email;
    }

    @Override
    public String toString() {
        return "MoCustomer{" +
                "CS_ID=" + CS_ID +
                ", CS_Name='" + CS_Name + '\'' +
                ", CS_Phone='" + CS_Phone + '\'' +
                ", CS_Email='" + CS_Email + '\'' +
                '}';
    }
}
