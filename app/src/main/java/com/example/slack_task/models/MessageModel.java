package com.example.slack_task.models;

import com.google.gson.annotations.SerializedName;

public class MessageModel {

    @SerializedName("id")
    private int id;
    @SerializedName("type")
    private String type;
    @SerializedName("channel")
    private String channel;
    @SerializedName("text")
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
