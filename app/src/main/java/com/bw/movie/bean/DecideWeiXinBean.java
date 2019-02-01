package com.bw.movie.bean;


import com.bw.movie.utils.BaseEnity;

/**
 * date:2019/1/27
 * author:李凯
 * function：微信号是否绑定
 */
public class DecideWeiXinBean extends BaseEnity {

    private String message;
    private String status;
    private int bindStatus;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(int bindStatus) {
        this.bindStatus = bindStatus;
    }
}
