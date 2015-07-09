package com.limingyilr.hongxin.bean;

import java.util.Arrays;

/**
 * Created by 翌日黄昏 on 2014/12/7.
 */
public class InfoData {
    private int status;
    private long[] msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long[] getMsg() {
        return msg;
    }

    public void setMsg(long[] msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "InfoData{" +
                "status=" + status +
                ", msg=" + Arrays.toString(msg) +
                '}';
    }
}
