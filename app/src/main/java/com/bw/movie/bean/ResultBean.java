package com.bw.movie.bean;

/**
 * date:2019/1/27
 * author:李凯
 * function：微信号登录
 */
public class ResultBean {
    private String sessionId;
    private int userId;
    private UserInfoBean userInfo;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "sessionId='" + sessionId + '\'' +
                ", userId=" + userId +
                ", userInfo=" + userInfo +
                '}';
    }
}
