package com.example.demo2.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

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

    private Date inday;

    private Date outday;

    private Date soutday;

    private String money;

    private  String roomid;

    private String  type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public Date getInday() {
        return inday;
    }

    public void setInday(Date inday) {
        this.inday = inday;
    }

    public Date getOutday() {
        return outday;
    }

    public void setOutday(Date outday) {
        this.outday = outday;
    }

    public Date getSoutday() {
        return soutday;
    }

    public void setSoutday(Date soutday) {
        this.soutday = soutday;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
