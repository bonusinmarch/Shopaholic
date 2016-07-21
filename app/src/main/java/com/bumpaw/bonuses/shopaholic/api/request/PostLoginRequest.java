package com.bumpaw.bonuses.shopaholic.api.request;

import com.bumpaw.bonuses.shopaholic.api.BaseApi;
import com.bumpaw.bonuses.shopaholic.api.response.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 21/07/2016.
 */
public class PostLoginRequest extends BaseApi {

    onPostLoginRequestListener onPostLoginRequestListener;
    RequestParams requestParams;
    AsyncHttpClient client;

    public PostLoginRequest(){
        client = getHttpClient();
    }

    public PostLoginRequest.onPostLoginRequestListener getOnPostLoginRequestListener() {
        return onPostLoginRequestListener;
    }

    public void setOnPostLoginRequestListener(PostLoginRequest.onPostLoginRequestListener onPostLoginRequestListener) {
        this.onPostLoginRequestListener = onPostLoginRequestListener;
    }

    public RequestParams getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(RequestParams requestParams) {
        this.requestParams = requestParams;
    }

    public interface onPostLoginRequestListener {
        void onPostLoginSuccess(User user);
        void onPostLoginFailure(String errorMessage);
    }

    @Override
    public void callApi() {
        super.callApi();
        client.post(POST_LOGIN, getRequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                User mUser = getuser(response);
                if (mUser.getStatus().equals("200")){
                    getOnPostLoginRequestListener().onPostLoginSuccess(mUser);
                }else {
                    getOnPostLoginRequestListener().onPostLoginFailure(mUser.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                getOnPostLoginRequestListener().onPostLoginFailure("Could Not Connect");

            }
        });
    }

    @Override
    public void cancelRequest() {
        super.cancelRequest();
        if (client != null){
            client.cancelAllRequests(true);
        }
    }

    private User getuser(String response){
        User user = null;
        try {

            JSONObject jsonObject = new JSONObject(response);
            user = new User();
            if (jsonObject.getString("status").equals("200")){

                user.setStatus(jsonObject.getString("status"));
                user.setEmail(jsonObject.getJSONObject("user").getString("email"));
                user.setUserId(jsonObject.getJSONObject("user").getString("user_id"));
                user.setUserName(jsonObject.getJSONObject("user").getString("username"));
            }else {
                user.setStatus(jsonObject.optString("status"));
                user.setMessage(jsonObject.optString("message"));
            }


        } catch (Exception e) {
            return null;
        }
        return user;
    }


}
