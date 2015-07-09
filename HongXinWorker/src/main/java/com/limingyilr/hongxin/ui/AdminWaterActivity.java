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
import com.limingyilr.hongxin.adapter.WaterAdapter;
import com.limingyilr.hongxin.adapter.WorkerAdapter;
import com.limingyilr.hongxin.bean.BaseData;
import com.limingyilr.hongxin.bean.Water;
import com.limingyilr.hongxin.bean.WaterData;
import com.limingyilr.hongxin.bean.Worker;
import com.limingyilr.hongxin.bean.WorkerData;
import com.limingyilr.hongxin.dao.MyRequest;
import com.limingyilr.hongxin.dao.NetUtil;
import com.limingyilr.hongxin.utils.SharedPreferencesManager;

import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmy on 2014/12/7.
 */
public class AdminWaterActivity extends ActionBarActivity implements PullToRefreshBase.OnRefreshListener, AdapterView.OnItemClickListener {
    private static final String TAG = "AdminWaterActivity";

    private PullToRefreshListView listView;

    private WaterAdapter waterAdapter;
    private List<Water> waterList = new ArrayList<Water>();
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminwater);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        initView();
    }

    private void initView() {
        listView = (PullToRefreshListView) findViewById(R.id.adminwater_list);
        waterAdapter = new WaterAdapter(this, waterList);
        listView.setAdapter(waterAdapter);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        queryWater();
    }

    private void queryWater() {
        NetUtil.adminWater(sharedPreferencesManager.readUsername(), sharedPreferencesManager.readPassword(),
                new MyRequest.IRequestListener() {
                    @Override
                    public void onComplete(int result, String msg, Object obj) {
                        listView.onRefreshComplete();
                        if (obj != null && result == HttpStatus.SC_OK) {
                            String content = new String((byte[]) obj);
                            Log.v(TAG, content);
                            if (content.contains("\"status\":1")) {
                                WaterData waterData = new Gson().fromJson(content, WaterData.class);
                                waterList = waterData.getMsg();
                            } else {
                                waterList.clear();
                                BaseData baseData = new Gson().fromJson(content, BaseData.class);
                                Toast.makeText(AdminWaterActivity.this, baseData.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            waterAdapter.synchronize(waterList);
                            return;
                        }
                        Toast.makeText(AdminWaterActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        queryWater();
    }
}
