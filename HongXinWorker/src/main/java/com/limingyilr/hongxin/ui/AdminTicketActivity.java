package com.limingyilr.hongxin.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.limingyilr.hongxin.R;
import com.limingyilr.hongxin.adapter.TicketAdapter;
import com.limingyilr.hongxin.adapter.WaterAdapter;
import com.limingyilr.hongxin.bean.BaseData;
import com.limingyilr.hongxin.bean.Ticket;
import com.limingyilr.hongxin.bean.TicketData;
import com.limingyilr.hongxin.bean.WaterData;
import com.limingyilr.hongxin.bean.Worker;
import com.limingyilr.hongxin.dao.MyRequest;
import com.limingyilr.hongxin.dao.NetUtil;
import com.limingyilr.hongxin.utils.SharedPreferencesManager;
import com.limingyilr.hongxin.utils.WorkerStatus;

import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmy on 2014/12/7.
 */
public class AdminTicketActivity extends ActionBarActivity implements PullToRefreshBase.OnRefreshListener, AdapterView.OnItemClickListener {
    private static final String TAG = "AdminTicketActivity";

    private PullToRefreshListView listView;

    private TicketAdapter ticketAdapter;
    private List<Ticket> ticketList = new ArrayList<Ticket>();
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminticket);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        initView();
    }

    private void initView() {
        listView = (PullToRefreshListView) findViewById(R.id.adminticket_list);
        ticketAdapter = new TicketAdapter(this, ticketList);
        listView.setAdapter(ticketAdapter);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        queryTicket();
    }

    private void queryTicket() {
        NetUtil.adminTicket(sharedPreferencesManager.readUsername(), sharedPreferencesManager.readPassword(),
                new MyRequest.IRequestListener() {
                    @Override
                    public void onComplete(int result, String msg, Object obj) {
                        listView.onRefreshComplete();
                        if (obj != null && result == HttpStatus.SC_OK) {
                            String content = new String((byte[]) obj);
                            Log.v(TAG, content);
                            if (content.contains("\"status\":1")) {
                                TicketData ticketData = new Gson().fromJson(content, TicketData.class);
                                ticketList = ticketData.getMsg();
                            } else {
                                ticketList.clear();
                                BaseData baseData = new Gson().fromJson(content, BaseData.class);
                                Toast.makeText(AdminTicketActivity.this, baseData.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            ticketAdapter.synchronize(ticketList);
                            return;
                        }
                        Toast.makeText(AdminTicketActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showOpWindow(ticketList.get(position - 1));
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        queryTicket();
    }

    protected void showOpWindow(final Ticket ticket) {
        // TODO Auto-generated method stub
        String msg = "确定给" + ticket.getSchool() + " " + ticket.getRoom() + "增加" + ticket.getAmount() + "张水票?";
        AlertDialog dialog = new AlertDialog.Builder(AdminTicketActivity.this).setTitle("操作").setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        op(ticket.getOrderId());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        dialog.show();
    }

    private void op(String orderId) {
        NetUtil.compTicket(orderId, sharedPreferencesManager.readUsername(), sharedPreferencesManager.readPassword(),
                new MyRequest.IRequestListener() {
                    @Override
                    public void onComplete(int result, String msg, Object obj) {
                        if (obj != null && result == HttpStatus.SC_OK) {
                            String content = new String((byte[]) obj);
                            Log.v(TAG, content);
                            BaseData baseData = new Gson().fromJson(content, BaseData.class);
                            if (baseData.getStatus() == 1) {
                                queryTicket();
                            } else {
                            }
                            Toast.makeText(AdminTicketActivity.this, baseData.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(AdminTicketActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
