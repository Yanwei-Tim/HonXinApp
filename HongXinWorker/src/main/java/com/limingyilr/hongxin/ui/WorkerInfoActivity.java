package com.limingyilr.hongxin.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.limingyilr.hongxin.R;
import com.limingyilr.hongxin.bean.AcceptData;
import com.limingyilr.hongxin.bean.BaseData;
import com.limingyilr.hongxin.dao.MyRequest;
import com.limingyilr.hongxin.dao.NetUtil;
import com.limingyilr.hongxin.utils.SharedPreferencesManager;

import org.apache.http.HttpStatus;

/**
 * Created by lmy on 2014/12/6.
 */
public class WorkerInfoActivity extends ActionBarActivity {
    private static final String TAG = "WorkerInfoActivity";

    private DatePicker startDate;
    private DatePicker endDate;
    private Button enter;
    private TextView text;

    private ProgressDialog progressDialog;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        setContentView(R.layout.activity_workerinfo);
        initView();
    }

    private void initView() {
        startDate = (DatePicker) findViewById(R.id.workerinfo_start);
        endDate = (DatePicker) findViewById(R.id.workerinfo_end);
        enter = (Button) findViewById(R.id.workerinfo_enter);
        text = (TextView) findViewById(R.id.workerinfo_text);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryInfo();
            }
        });
    }

    private void queryInfo() {
        final String start = startDate.getYear() + "-" + (startDate.getMonth() + 1) + "-" + startDate.getDayOfMonth();
        final String end = endDate.getYear() + "-" + (endDate.getMonth() + 1) + "-" + endDate.getDayOfMonth();
        NetUtil.queryWorkerInfo(sharedPreferencesManager.readId(), start, end,
                new MyRequest.IRequestListener() {
                    @Override
                    public void onComplete(int result, String msg, Object obj) {
                        if (obj != null && result == HttpStatus.SC_OK) {
                            String content = new String((byte[]) obj);
                            BaseData baseData = new Gson().fromJson(content, BaseData.class);
                            if (baseData.getStatus() == 1) {
                                text.setText("从" + start + "到" + end + "您完成的订单量为:" + baseData.getMsg());
                                return;
                            } else {
                                Toast.makeText(WorkerInfoActivity.this, baseData.getMsg(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        Toast.makeText(WorkerInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
