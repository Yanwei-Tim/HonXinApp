package com.limingyilr.hongxin.bean;

import java.util.List;

/**
 * Created by lmy on 2014/12/4.
 */
public class AcceptData {
    private int status;
    private List<AcceptOrder> msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<AcceptOrder> getMsg() {
        return msg;
    }

    public void setMsg(List<AcceptOrder> msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AcceptData{" +
                "status=" + status +
                ", msg=" + msg +
                '}';
    }
}
