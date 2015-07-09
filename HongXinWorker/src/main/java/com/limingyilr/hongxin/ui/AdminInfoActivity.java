package com.limingyilr.hongxin.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.limingyilr.hongxin.R;
import com.limingyilr.hongxin.bean.BaseData;
import com.limingyilr.hongxin.bean.InfoData;
import com.limingyilr.hongxin.bean.WorkerData;
import com.limingyilr.hongxin.dao.MyRequest;
import com.limingyilr.hongxin.dao.NetUtil;
import com.limingyilr.hongxin.utils.SharedPreferencesManager;

import org.apache.http.HttpStatus;

/**
 * Created by lmy on 2014/12/7.
 */
public class AdminInfoActivity extends ActionBarActivity {
    private static final String TAG = "AdminInfoActivity";

    private TextView userText;
    private TextView workerText;
    private TextView ticketText;
    private Button button;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admininfo);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        initView();
    }

    private void initView() {
        userText = (TextView) findViewById(R.id.admininfo_user);
        workerText = (TextView) findViewById(R.id.admininfo_worker);
        ticketText = (TextView) findViewById(R.id.admininfo_ticket);
        button = (Button) findViewById(R.id.admininfo_enter);

        queryInfo();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryInfo();
            }
        });
    }

    private void queryInfo() {
        NetUtil.adminInfo(sharedPreferencesManager.readUsername(),
                sharedPreferencesManager.readPassword(), new MyRequest.IRequestListener() {
                    @Override
                    public void onComplete(int result, String msg, Object obj) {
                        if (obj != null && result == HttpStatus.SC_OK) {
                            String content = new String((byte[]) obj);
                            Log.v(TAG, content);
                            if (content.contains("\"status\":1")) {
                                InfoData infoData = new Gson().fromJson(content, InfoData.class);
                                userText.setText(infoData.getMsg()[0] + " / " + infoData.getMsg()[1]);
                                workerText.setText(infoData.getMsg()[2] + " / " + infoData.getMsg()[3]);
                                ticketText.setText(infoData.getMsg()[4] + "");
                            } else {
                                BaseData baseData = new Gson().fromJson(content, BaseData.class);
                                Toast.makeText(AdminInfoActivity.this, baseData.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }
                        Toast.makeText(AdminInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
