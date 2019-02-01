package com.bw.movie.bean;

import com.bw.movie.utils.BaseEnity;

/**
 * date:2019/1/27
 * author:李凯
 * function：微信号绑定
 */

public class BindWeiXinBean extends BaseEnity {

    private String message;
    private String status;

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
}
