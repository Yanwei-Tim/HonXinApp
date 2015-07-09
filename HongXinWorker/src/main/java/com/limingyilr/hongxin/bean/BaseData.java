package com.limingyilr.hongxin.bean;

/**
 * Created by lmy on 2014/12/4.
 */
public class BaseData {
    private int status;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
