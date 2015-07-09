package com.limingyilr.hongxin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.limingyilr.hongxin.R;
import com.limingyilr.hongxin.adapter.AccepteAdapter;
import com.limingyilr.hongxin.bean.AcceptData;
import com.limingyilr.hongxin.bean.AcceptOrder;
import com.limingyilr.hongxin.bean.BaseData;
import com.limingyilr.hongxin.bean.LoginData;
import com.limingyilr.hongxin.dao.MyRequest;
import com.limingyilr.hongxin.dao.NetUtil;
import com.limingyilr.hongxin.utils.SharedPreferencesManager;

import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmy on 2014/12/3.
 */
public class WorkerActivity extends ActionBarActivity implements PullToRefreshBase.OnRefreshListener, AdapterView.OnItemClickListener {
    private static final String TAG = "WorkerActivity";

    private PullToRefreshListView listView;
    private Button numberBtn;
    private Button uncomBtn;

    private AccepteAdapter accepteAdapter;
    private SharedPreferencesManager sharedPreferencesManager;
    private List<AcceptOrder> acceptOrders = new ArrayList<AcceptOrder>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        initView();
    }

    private void initView() {
        listView = (PullToRefreshListView) findViewById(R.id.worker_list);
        numberBtn = (Button) findViewById(R.id.worker_number);
        uncomBtn = (Button) findViewById(R.id.worker_uncom);

        accepteAdapter = new AccepteAdapter(this, acceptOrders);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        listView.setAdapter(accepteAdapter);
        queryWater();

        uncomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkerActivity.this, WaterUncomActivity.class);
                startActivity(intent);
            }
        });
        numberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkerActivity.this, WorkerInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void queryWater() {
        NetUtil.queryWater(sharedPreferencesManager.readId(), new MyRequest.IRequestListener() {
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
                        Toast.makeText(WorkerActivity.this, baseData.getMsg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    accepteAdapter.synchronize(acceptOrders);
                    return;
                }
                Toast.makeText(WorkerActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void accpetWater(String orderId, final int position) {
        NetUtil.acceptWater(sharedPreferencesManager.readId(), orderId, new MyRequest.IRequestListener() {
            @Override
            public void onComplete(int result, String msg, Object obj) {
                if (obj != null && result == HttpStatus.SC_OK) {
                    String content = new String((byte[]) obj);
                    BaseData baseData = new Gson().fromJson(content, BaseData.class);
                    if (baseData == null) {
                        Toast.makeText(WorkerActivity.this, "抢单失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (baseData.getStatus() == 1) {
                        acceptOrders.remove(position);
                        accepteAdapter.synchronize(acceptOrders);
                    }
                    Toast.makeText(WorkerActivity.this, baseData.getMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(WorkerActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        queryWater();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        accpetWater(acceptOrders.get(position - 1).getOrderId(), position - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        sharedPreferencesManager.init();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}
