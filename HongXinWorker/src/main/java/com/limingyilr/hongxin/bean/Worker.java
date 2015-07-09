package com.limingyilr.hongxin.bean;

/**
 * Created by lmy on 2014/12/6.
 */
public class Worker {
    private String openId;
    private String name;
    private String phoneNumber;
    private String workerId;
    private String completedAmount;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getCompletedAmount() {
        return completedAmount;
    }

    public void setCompletedAmount(String completedAmount) {
        this.completedAmount = completedAmount;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "openId='" + openId + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", workerId='" + workerId + '\'' +
                ", completedAmount='" + completedAmount + '\'' +
                '}';
    }
}
