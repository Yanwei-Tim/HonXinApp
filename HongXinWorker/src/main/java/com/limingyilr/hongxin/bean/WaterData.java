package com.limingyilr.hongxin.bean;

import java.util.List;

/**
 * Created by lmy on 2014/12/7.
 */
public class WaterData {
    private int status;
    private List<Water> msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Water> getMsg() {
        return msg;
    }

    public void setMsg(List<Water> msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "WaterData{" +
                "status=" + status +
                ", msg=" + msg +
                '}';
    }
}
