package com.example.slack_task.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class channelListModel {

    @SerializedName("ok")
    private boolean ok;
    @SerializedName("channels")
    public List<ChannelDetails> channelDetails;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<ChannelDetails> getChannelDetails() {
        return channelDetails;
    }

    public void setChannelDetails(List<ChannelDetails> channelDetails) {
        this.channelDetails = channelDetails;
    }

    public class ChannelDetails {

        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("num_members")
        private int num_members;
        @SerializedName("purpose")
        public Purpose purpose;




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

        public int getNum_members() {
            return num_members;
        }

        public void setNum_members(int num_members) {
            this.num_members = num_members;
        }

        public Purpose getPurpose() {
            return purpose;
        }

        public void setPurpose(Purpose purpose) {
            this.purpose = purpose;
        }

        public class Purpose {

            @SerializedName("value")
            private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }

}
