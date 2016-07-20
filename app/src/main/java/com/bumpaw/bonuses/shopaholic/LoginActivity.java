package com.bumpaw.bonuses.shopaholic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener{

    private TextView tv_reg;
    private Button btn_login;
    private AppPreference appPreference;
    private EditText edt_username, edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        appPreference = new AppPreference(LoginActivity.this);

        edt_password = (EditText)findViewById(R.id.ed_pass);
        edt_username = (EditText)findViewById(R.id.ed_username);

        tv_reg = (TextView)findViewById(R.id.tv_register);
        tv_reg.setOnClickListener(this);

        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent =null;
        boolean isLogin = false;
        switch (v.getId()){
            case R.id.tv_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.btn_login:
                String username = edt_username.getText().toString().trim();
                String password = edt_password.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else {
                    appPreference.setUsername(username);
                    intent = new Intent(LoginActivity.this, HomeActivity.class);
                    isLogin = true;
                }
                break;
        }

        if(intent != null) {
            startActivity(intent);
            if (isLogin){
                finish();
            }
        }


    }
}
