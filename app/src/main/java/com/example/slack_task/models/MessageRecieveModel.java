package com.example.slack_task.models;

import com.google.gson.annotations.SerializedName;

public class MessageRecieveModel {


    /* @SerializedName("ok")
        val ok: Boolean,

        @SerializedName("id")
        val id: String?,

        @SerializedName("reply_to")
        val replyTo: String?,
        @SerializedName("type")
        val type: String?,

        @SerializedName("ts")
        val ts: String?,

        @SerializedName("text")
        val text: String?,
*/

    @SerializedName("ok")
    private boolean ok;
    @SerializedName("id")
    private String id;
    @SerializedName("reply_to")
    private String reply_to;
    @SerializedName("type")
    private String type;
   /* @SerializedName("user")
    private String user;*/
    @SerializedName("text")
    private String text;
    @SerializedName("user")
    private String user;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReply_to() {
        return reply_to;
    }

    public void setReply_to(String reply_to) {
        this.reply_to = reply_to;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

   /* public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }*/

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}


