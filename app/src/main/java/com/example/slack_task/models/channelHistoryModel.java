package com.example.slack_task.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class channelHistoryModel {

    @SerializedName("ok")
    private boolean ok;
    @SerializedName("latest")
    private String latest;
    @SerializedName("has_more")
    private boolean has_more;
    @SerializedName("messages")
    private List<messageModel> messageFromChannel;


    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public List<messageModel> getMessageFromChannel() {
        return messageFromChannel;
    }

    public void setMessageFromChannel(List<messageModel> messageFromChannel) {
        this.messageFromChannel = messageFromChannel;
    }

    public static class messageModel {

        @SerializedName("type")
        private String  type;
        @SerializedName("user")
        private String user;
        @SerializedName("text")
        private String text;
        @SerializedName("ts")
        private String ts;
        @SerializedName("username")
        private String username;


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }
    }
}
