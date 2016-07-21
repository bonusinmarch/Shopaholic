package com.bumpaw.bonuses.shopaholic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bumpaw.bonuses.shopaholic.api.request.PostRegisterRequest;
import com.bumpaw.bonuses.shopaholic.api.response.BaseResponse;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements
        PostRegisterRequest.onPostRegisterRequestListener,
        View.OnClickListener {

    EditText edt_reguser, edt_regpass, edt_regemail;
    Button btn_regg;
    CheckBox cb_reg;

    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.cb_toc)
    CheckBox cbToc;
    @BindView(R.id.btn_reg)
    Button btnReg;

    ProgressDialog progresDialog;
    PostRegisterRequest postRegisterRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        progresDialog = new ProgressDialog(RegisterActivity.this);
        progresDialog.setTitle("register");
        progresDialog.setMessage("Please Wait");

        postRegisterRequest = new PostRegisterRequest();
        postRegisterRequest.setOnPostRegisterRequestListener(this);


        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostRegisterSuccess(BaseResponse response) {
        progresDialog.cancel();
        Toast.makeText(RegisterActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

        startActivity(intent);
        finish();


    }

    @Override
    public void onPostRegisterFailure(String errorMessage) {
        progresDialog.cancel();
        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }



    @OnClick({R.id.edt_username, R.id.edt_email, R.id.edt_password, R.id.cb_toc, R.id.btn_reg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_username:
                break;
            case R.id.edt_email:
                break;
            case R.id.edt_password:
                break;
            case R.id.cb_toc:
                break;
            case R.id.btn_reg:
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else {
                    if (cbToc.isChecked()){
                        RequestParams requestParams = new RequestParams();
                        requestParams.put("username", username);
                        requestParams.put("password", password);
                        requestParams.put("email", email);

                        postRegisterRequest.setPostRequestParams(requestParams);
                        progresDialog.show();
                        postRegisterRequest.callApi();
                    }else {
                        Toast.makeText(RegisterActivity.this, "Please check the TOC", Toast.LENGTH_SHORT).show();

                    }

                }
                break;
        }
    }
}
