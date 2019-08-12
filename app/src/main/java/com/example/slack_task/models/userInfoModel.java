package com.example.slack_task.models;

import com.google.gson.annotations.SerializedName;

public class userInfoModel {

    @SerializedName("ok")
    private boolean ok;
    @SerializedName("user")
    private User user;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User {

        @SerializedName("id")
        private String id;
        @SerializedName("team_id")
        private String team_id;
        @SerializedName("name")
        private String name="";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTeam_id() {
            return team_id;
        }

        public void setTeam_id(String team_id) {
            this.team_id = team_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
