package com.hackthon.android.iotdemoapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mayur Lathkar on 7/12/2016.
 */
public class User implements Serializable {

    @SerializedName("name")
    private String userName;

    @SerializedName("phone")
    private String userPhone;

    @SerializedName("token")
    private String userToken;

    @SerializedName("id")
    private String id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
