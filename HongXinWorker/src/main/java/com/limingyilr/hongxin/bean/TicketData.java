package com.limingyilr.hongxin.bean;

import java.util.List;

/**
 * Created by lmy on 2014/12/7.
 */
public class TicketData {
    private int status;
    private List<Ticket> msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Ticket> getMsg() {
        return msg;
    }

    public void setMsg(List<Ticket> msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "TicketData{" +
                "status=" + status +
                ", msg=" + msg +
                '}';
    }
}
