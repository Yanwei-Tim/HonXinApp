package com.limingyilr.hongxin.bean;

/**
 * Created by lmy on 2014/12/3.
 */
public class LoginData {
    private int status;
    private int type;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "status=" + status +
                ", type=" + type +
                ", msg='" + msg + '\'' +
                '}';
    }
}
