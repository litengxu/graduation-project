package com.example.demo2.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="History")
public class History {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String sex;

    private String card;

    private String phone;

    private Long number;

    private String inday;

    private String outday;

    private String soutday;

    private String money;

    private  String roomid;

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getInday() {
        return inday;
    }

    public void setInday(String inday) {
        this.inday = inday;
    }

    public String getOutday() {
        return outday;
    }

    public void setOutday(String outday) {
        this.outday = outday;
    }

    public String getSoutday() {
        return soutday;
    }

    public void setSoutday(String soutday) {
        this.soutday = soutday;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
