package com.bumpaw.bonuses.shopaholic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener{

    private TextView tv_reg;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        tv_reg = (TextView)findViewById(R.id.tv_register);
        tv_reg.setOnClickListener(this);

        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent =null;
        switch (v.getId()){
            case R.id.tv_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.btn_login:
                intent = new Intent(LoginActivity.this, MainActivity.class);
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }


    }
}
