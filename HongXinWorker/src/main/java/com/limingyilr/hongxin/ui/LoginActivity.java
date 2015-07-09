package com.limingyilr.hongxin.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.limingyilr.hongxin.R;
import com.limingyilr.hongxin.bean.LoginData;
import com.limingyilr.hongxin.dao.MyRequest;
import com.limingyilr.hongxin.dao.NetUtil;
import com.limingyilr.hongxin.utils.SharedPreferencesManager;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends ActionBarActivity {
    private static final String TAG = "LoginActivity";

    private EditText userNameEdit;
    private EditText passwordEdit;
    private Button enterBtn;
    private CheckBox checkBox;

    private ProgressDialog progressDialog;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        if (sharedPreferencesManager.readAutoLogin()) {
            Intent intent = null;
            if (sharedPreferencesManager.readType() == 0) {
                intent = new Intent(this, AdminActivity.class);
            } else if (sharedPreferencesManager.readType() == 1) {
                intent = new Intent(this, WorkerActivity.class);
            }
            startActivity(intent);
            this.finish();
            return;
        }
        sharedPreferencesManager.init();
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        userNameEdit = (EditText) findViewById(R.id.login_username);
        passwordEdit = (EditText) findViewById(R.id.login_password);
        enterBtn = (Button) findViewById(R.id.login_enter);
        checkBox = (CheckBox) findViewById(R.id.login_check);

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin();
            }
        });
    }

    private void startLogin() {
        if (userNameEdit.getText() == null || userNameEdit.getText().equals("")) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordEdit.getText() == null || passwordEdit.getText().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在登录...");
        progressDialog.show();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", userNameEdit.getText().toString()));
        params.add(new BasicNameValuePair("password", passwordEdit.getText().toString()));
        JSONObject jsonObject = new JSONObject();
        NetUtil.Login(params, new MyRequest.IRequestListener() {
            @Override
            public void onComplete(int result, String msg, Object obj) {
                progressDialog.dismiss();
                if (obj != null && result == HttpStatus.SC_OK) {
                    String content = new String((byte[]) obj);
                    Log.v(TAG, content);
                    LoginData loginData = new Gson().fromJson(content, LoginData.class);
                    if (loginData.getStatus() == 1) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        startIntent(loginData);
                        return;
                    }else {
                        Toast.makeText(LoginActivity.this, loginData.getMsg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startIntent(LoginData loginData) {
        if (checkBox.isChecked()) {
            sharedPreferencesManager.saveAutoLogin(true);
            sharedPreferencesManager.saveUsername(userNameEdit.getText().toString());
            sharedPreferencesManager.savePassword(passwordEdit.getText().toString());
        }
        sharedPreferencesManager.saveId(loginData.getMsg());
        sharedPreferencesManager.saveType(loginData.getType());
        Intent intent = null;
        if (loginData.getType() == 0) {
            intent = new Intent(this, AdminActivity.class);
        } else if (loginData.getType() == 1) {
            intent = new Intent(this, WorkerActivity.class);
        }
        startActivity(intent);
        this.finish();
    }

}
