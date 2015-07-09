package com.limingyilr.hongxin.bean;

/**
 * Created by lmy on 2014/12/3.
 */
public class AcceptOrder {
    private String orderId;
    private String time;
    private String school;
    private String roomName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "AcceptOrder{" +
                "orderId='" + orderId + '\'' +
                ", time='" + time + '\'' +
                ", school='" + school + '\'' +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
