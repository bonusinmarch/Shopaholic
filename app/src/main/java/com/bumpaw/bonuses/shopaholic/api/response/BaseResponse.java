package com.bumpaw.bonuses.shopaholic.api.response;

/**
 * Created by user on 21/07/2016.
 */
public class BaseResponse {
    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
