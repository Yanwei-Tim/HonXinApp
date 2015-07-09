package com.limingyilr.hongxin.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.limingyilr.hongxin.R;
import com.limingyilr.hongxin.adapter.AccepteAdapter;
import com.limingyilr.hongxin.bean.AcceptData;
import com.limingyilr.hongxin.bean.AcceptOrder;
import com.limingyilr.hongxin.bean.BaseData;
import com.limingyilr.hongxin.dao.MyRequest;
import com.limingyilr.hongxin.dao.NetUtil;
import com.limingyilr.hongxin.utils.SharedPreferencesManager;

import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmy on 2014/12/5.
 */
public class WaterUncomActivity extends ActionBarActivity implements PullToRefreshBase.OnRefreshListener {
    private static final String TAG = "WaterUncomActivity";

    private PullToRefreshListView listView;

    private AccepteAdapter accepteAdapter;
    private SharedPreferencesManager sharedPreferencesManager;
    private List<AcceptOrder> acceptOrders = new ArrayList<AcceptOrder>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wateruncom);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        initView();
    }

    private void initView() {
        listView = (PullToRefreshListView) findViewById(R.id.wateruncom_list);

        accepteAdapter = new AccepteAdapter(this, acceptOrders);
        accepteAdapter.setViewTip(false);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(this);
        listView.setAdapter(accepteAdapter);
        queryWater();
    }

    private void queryWater() {
        NetUtil.waterUncom(sharedPreferencesManager.readId(), new MyRequest.IRequestListener() {
            @Override
            public void onComplete(int result, String msg, Object obj) {
                listView.onRefreshComplete();
                if (obj != null && result == HttpStatus.SC_OK) {
                    String content = new String((byte[]) obj);
                    if (content.contains("\"status\":1")) {
                        AcceptData acceptData = new Gson().fromJson(content, AcceptData.class);
                        acceptOrders = acceptData.getMsg();
                    } else {
                        acceptOrders.clear();
                        BaseData baseData = new Gson().fromJson(content, BaseData.class);
                        Toast.makeText(WaterUncomActivity.this, baseData.getMsg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    accepteAdapter.synchronize(acceptOrders);
                    return;
                }
                Toast.makeText(WaterUncomActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        queryWater();
    }
}
