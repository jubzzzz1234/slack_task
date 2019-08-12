package com.example.slack_task.models;

import com.google.gson.annotations.SerializedName;

public class rtmConnectModel {

    @SerializedName("ok")
    private boolean ok;
    @SerializedName("self")
    private SelfModel selfModel;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public SelfModel getSelfModel() {
        return selfModel;
    }

    public void setSelfModel(SelfModel selfModel) {
        this.selfModel = selfModel;
    }

    public class SelfModel {

        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
