package com.bumpaw.bonuses.shopaholic.api.response;

/**
 * Created by user on 21/07/2016.
 */
public class User extends BaseResponse{
    private String userId;
    private String userName;
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
