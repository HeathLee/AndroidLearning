package com.example.heath.hw_7;

import java.io.Serializable;

/**
 * Created by heath on 15-12-4.
 */
public class Contact implements Serializable {
    private String sid;
    private String name;
    private String phone;

    public Contact() {}

    public Contact(String sid, String name, String phone) {
        this.sid = sid;
        this.name = name;
        this.phone = phone;
    }

    public String getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
