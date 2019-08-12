package com.example.slack_task.models;

import com.google.gson.annotations.SerializedName;

public class tokenModel {
    @SerializedName("ok")
    private String ok;

    @SerializedName("access_token")
    private String access_token;
    @SerializedName("scope")
    private String scope;
    @SerializedName("user_id")
    private String user_id;
     public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
