package com.limingyilr.hongxin.bean;

/**
 * Created by lmy on 2014/12/7.
 */
public class Ticket {
    private String orderId;
    private String amount;
    private String time;
    private String school;
    private String room;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "orderId='" + orderId + '\'' +
                ", amount='" + amount + '\'' +
                ", time='" + time + '\'' +
                ", school='" + school + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
