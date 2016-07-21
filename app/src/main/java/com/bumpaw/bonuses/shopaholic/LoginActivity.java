package com.bumpaw.bonuses.shopaholic;

import android.app.ProgressDialog;
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

import com.bumpaw.bonuses.shopaholic.api.request.PostLoginRequest;
import com.bumpaw.bonuses.shopaholic.api.response.User;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener,
        PostLoginRequest.onPostLoginRequestListener{

    private TextView tv_reg;
    private Button btn_login;
    private AppPreference appPreference;
    private EditText edt_username, edt_password;

    private PostLoginRequest postLoginRequest;
    private ProgressDialog progressDialog;

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

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Logging In");

        postLoginRequest = new PostLoginRequest();
        postLoginRequest.setOnPostLoginRequestListener(this);



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

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("username", username);
                    requestParams.put("password", password);

                    postLoginRequest.setRequestParams(requestParams);
                    progressDialog.show();
                    postLoginRequest.callApi();


                }
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }


    }

    @Override
    public void onPostLoginSuccess(User user) {

        progressDialog.cancel();
        appPreference.setUserid(user.getUserId());

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onPostLoginFailure(String errorMessage) {

        progressDialog.cancel();
        Toast.makeText(LoginActivity.this, "Wrong password / username", Toast.LENGTH_SHORT).show();

    }
}
