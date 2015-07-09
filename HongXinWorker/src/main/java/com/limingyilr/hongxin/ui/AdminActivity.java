package com.limingyilr.hongxin.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.limingyilr.hongxin.R;
import com.limingyilr.hongxin.adapter.WorkerAdapter;
import com.limingyilr.hongxin.bean.AcceptData;
import com.limingyilr.hongxin.bean.BaseData;
import com.limingyilr.hongxin.bean.Worker;
import com.limingyilr.hongxin.bean.WorkerData;
import com.limingyilr.hongxin.dao.MyRequest;
import com.limingyilr.hongxin.dao.NetUtil;
import com.limingyilr.hongxin.utils.SharedPreferencesManager;
import com.limingyilr.hongxin.utils.WorkerStatus;

import org.apache.http.HttpStatus;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmy on 2014/12/3.
 */
public class AdminActivity extends ActionBarActivity implements PullToRefreshBase.OnRefreshListener, AdapterView.OnItemClickListener {
    private static final String TAG = "AdminActivity";

    private PullToRefreshListView listView;
    private Button infoBtn;
    private Button ticketBtn;
    private Button waterBtn;

    private SharedPreferencesManager sharedPreferencesManager;
    private WorkerAdapter workerAdapter;
    private List<Worker> workerList = new ArrayList<Worker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        initView();
    }

    private void initView() {
        listView = (PullToRefreshListView) findViewById(R.id.admin_list);
        infoBtn = (Button) findViewById(R.id.admin_info);
        ticketBtn = (Button) findViewById(R.id.admin_ticket);
        waterBtn = (Button) findViewById(R.id.admin_water);

        workerAdapter = new WorkerAdapter(this, workerList);
        listView.setAdapter(workerAdapter);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        queryWorker();

        infoBtn.setOnClickListener(listener);
        ticketBtn.setOnClickListener(listener);
        waterBtn.setOnClickListener(listener);
    }

    private void queryWorker() {
        String json = "{\"admin\":\"" + sharedPreferencesManager.readUsername() + "\",\"adminPw\":\"" + sharedPreferencesManager.readPassword() + "\"}";
        NetUtil.queryWorker(json, new MyRequest.IRequestListener() {
            @Override
            public void onComplete(int result, String msg, Object obj) {
                listView.onRefreshComplete();
                if (obj != null && result == HttpStatus.SC_OK) {
                    String content = new String((byte[]) obj);
                    Log.v(TAG, content);
                    System.out.println(content);
                    if (content.contains("\"status\":1")) {
                        WorkerData workerData = new Gson().fromJson(content, WorkerData.class);
                        workerList = workerData.getMsg();
                    } else {
                        workerList.clear();
                        BaseData baseData = new Gson().fromJson(content, BaseData.class);
                        Toast.makeText(AdminActivity.this, baseData.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    workerAdapter.synchronize(workerList);
                    return;
                }
                Toast.makeText(AdminActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        queryWorker();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showOpWindow(workerList.get(position - 1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
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
        if (id == R.id.action_add) {
            showAddWindow();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        sharedPreferencesManager.init();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }


    private void showAddWindow() {
        View convertView = LayoutInflater.from(this).inflate(R.layout.dialog_add,
                null);
        final EditText name = (EditText) convertView.findViewById(R.id.dialog_add_username);
        final EditText pw = (EditText) convertView.findViewById(R.id.dialog_add_pw);
        final EditText ph = (EditText) convertView.findViewById(R.id.dialog_add_ph);
        AlertDialog dialog = new AlertDialog.Builder(AdminActivity.this).setTitle("添加工作人员")
                .setView(convertView).setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!name.getText().toString().equals("") &&
                                !pw.getText().toString().equals("") &&
                                !ph.getText().toString().equals(""))
                            add(name.getText().toString(), pw.getText().toString(), ph.getText().toString());
                        else
                            Toast.makeText(AdminActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
    }

    protected void showOpWindow(final Worker worker) {
        // TODO Auto-generated method stub
        String msg = "";
        String op = "";
        if (worker.getOpenId().equals(WorkerStatus.WORKING)) {
            msg = "是否解雇" + worker.getName() + "?";
            op = WorkerStatus.FIRED;
        } else {
            msg = "是否重新聘用" + worker.getName() + "?";
            op = WorkerStatus.WORKING;
        }
        AlertDialog dialog = new AlertDialog.Builder(AdminActivity.this).setTitle("操作").setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        op(worker);
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

    private void op(Worker worker) {
        String op = "";
        if (worker.getOpenId().equals(WorkerStatus.WORKING))
            op = WorkerStatus.FIRED;
        else
            op = WorkerStatus.WORKING;
        NetUtil.opWorker(op, worker.getWorkerId(), sharedPreferencesManager.readUsername(),
                sharedPreferencesManager.readPassword(), new MyRequest.IRequestListener() {
                    @Override
                    public void onComplete(int result, String msg, Object obj) {
                        if (obj != null && result == HttpStatus.SC_OK) {
                            String content = new String((byte[]) obj);
                            BaseData baseData = new Gson().fromJson(content, BaseData.class);
                            if (baseData.getStatus() == 1) {
                                queryWorker();
                            } else {
                            }
                            Toast.makeText(AdminActivity.this, baseData.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(AdminActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void add(String workerName, String password, String phoneNumber) {
        NetUtil.addWorker(workerName, password, phoneNumber,
                sharedPreferencesManager.readUsername(),
                sharedPreferencesManager.readPassword(), new MyRequest.IRequestListener() {
                    @Override
                    public void onComplete(int result, String msg, Object obj) {
                        if (obj != null && result == HttpStatus.SC_OK) {
                            String content = new String((byte[]) obj);
                            BaseData baseData = new Gson().fromJson(content, BaseData.class);
                            if (baseData.getStatus() == 1) {
                                queryWorker();
                            } else {
                            }
                            Toast.makeText(AdminActivity.this, baseData.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(AdminActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.admin_info:
                    intent = new Intent(AdminActivity.this, AdminInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.admin_ticket:
                    intent = new Intent(AdminActivity.this, AdminTicketActivity.class);
                    startActivity(intent);
                    break;
                case R.id.admin_water:
                    intent = new Intent(AdminActivity.this, AdminWaterActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
