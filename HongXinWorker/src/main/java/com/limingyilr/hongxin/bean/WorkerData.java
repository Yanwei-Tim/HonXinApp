package com.limingyilr.hongxin.bean;

import java.util.List;

/**
 * Created by lmy on 2014/12/6.
 */
public class WorkerData {
    private int status;
    private List<Worker> msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Worker> getMsg() {
        return msg;
    }

    public void setMsg(List<Worker> msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "WorkerData{" +
                "status=" + status +
                ", msg=" + msg +
                '}';
    }
}
