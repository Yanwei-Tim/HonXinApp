package com.limingyilr.hongxin.dao;

import com.limingyilr.hongxin.bean.Worker;
import com.limingyilr.hongxin.utils.Api;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by lmy on 2014/12/3.
 */
public class NetUtil {

    public static void Login(List<NameValuePair> params, MyRequest.IRequestListener requestListener) {
        MyRequest myRequest = MyRequest.getInstance();
        myRequest.post(Api.LOGIN, params, requestListener);
    }

    public static void queryWater(String workerId, MyRequest.IRequestListener requestListener) {
        MyRequest myRequest = MyRequest.getInstance();
        myRequest.get(Api.QUERYWATER + "?workerId=" + workerId, requestListener);
    }

    public static void acceptWater(String workerId, String orderId, MyRequest.IRequestListener requestListener) {
        MyRequest myRequest = MyRequest.getInstance();
        myRequest.get(Api.ACCEPTWATER + "?workerId=" + workerId + "&orderId=" + orderId, requestListener);
    }

    public static void waterUncom(String workerId, MyRequest.IRequestListener requestListener) {
        MyRequest myRequest = MyRequest.getInstance();
        myRequest.get(Api.WATERUNCOM + "?workerId=" + workerId, requestListener);
    }

    public static void queryWorkerInfo(String workerId, String start, String end, MyRequest.IRequestListener requestListener) {
        MyRequest myRequest = MyRequest.getInstance();
        myRequest.get(Api.QUERYWORKERINFO + "?workerId=" + workerId + "&start=" + start + "&end=" + end, requestListener);
    }

    public static void queryWorker(String json, MyRequest.IRequestListener requestListener) {
        MyRequest myRequest = MyRequest.getInstance();
        myRequest.get(Api.QUERYWORKER + "?data=" + URLEncoder.encode(json), requestListener);
    }

    public static void opWorker(String op, String workerId, String admin, String adminPw, MyRequest.IRequestListener requestListener) {
        MyRequest myRequest = MyRequest.getInstance();
        String json = "{\"workerId\":\"" + workerId +
                "\",\"admin\":\"" + admin +
                "\",\"adminPw\":\"" + adminPw + "\"}";
        myRequest.get(Api.OPWORKER + "?op=" + op + "&data=" + URLEncoder.encode(json), requestListener);
    }

    public static void addWorker(String workerName, String password, String phoneNumber, String admin, String adminPw, MyRequest.IRequestListener requestListener) {
        MyRequest myRequest = MyRequest.getInstance();
        String json = "{\"workerName\":\"" + workerName +
                "\",\"password\":\"" + password +
                "\",\"phoneNumber\":\"" + phoneNumber +
                "\",\"admin\":\"" + admin +
                "\",\"adminPw\":\"" + adminPw + "\"}";
        myRequest.get(Api.ADDWORKER + "?data=" + URLEncoder.encode(json), requestListener);
    }

    public static void adminWater(String admin, String adminPw, MyRequest.IRequestListener requestListener) {
        MyRequest myRequest = MyRequest.getInstance();
        String json = "{\"admin\":\"" + admin +
                "\",\"adminPw\":\"" + adminPw + "\"}";
        myRequest.get(Api.ADMINWATER + "?data=" + URLEncoder.encode(json), requestListener);
    }

    public static void adminTicket(String admin, String adminPw, MyRequest.IRequestListener requestListener) {
        MyRequest myRequest = MyRequest.getInstance();
        String json = "{\"admin\":\"" + admin +
                "\",\"adminPw\":\"" + adminPw + "\"}";
        myRequest.get(Api.ADMINTICKET + "?data=" + URLEncoder.encode(json), requestListener);
    }

    public static void compTicket(String orderId, String admin, String adminPw, MyRequest.IRequestListener requestListener) {
        MyRequest myRequest = MyRequest.getInstance();
        String json = "{\"orderId\":\"" + orderId +
                "\",\"admin\":\"" + admin +
                "\",\"adminPw\":\"" + adminPw + "\"}";
        myRequest.get(Api.COMPTICKET + "?data=" + URLEncoder.encode(json), requestListener);
    }
    public static void adminInfo(String admin, String adminPw, MyRequest.IRequestListener requestListener) {
        MyRequest myRequest = MyRequest.getInstance();
        String json = "{\"admin\":\"" + admin +
                "\",\"adminPw\":\"" + adminPw + "\"}";
        myRequest.get(Api.ADMININFO + "?data=" + URLEncoder.encode(json), requestListener);
    }
}
