package com.example.slack_task.retrofit;


import com.example.slack_task.Constants;
import com.example.slack_task.models.channelHistoryModel;
import com.example.slack_task.models.channelListModel;
import com.example.slack_task.models.rtmConnectModel;
import com.example.slack_task.models.tokenModel;
import com.example.slack_task.models.userInfoModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetDataService {



    @FormUrlEncoded
    @POST("oauth.access")
    Call<tokenModel> getAccessToken(
            @Field(Constants.CLIENT_ID_KEY) String client_id,
            @Field(Constants.CLIENT_SECRET_KEY) String client_secret,
            @Field(Constants.CODE_KEY) String code,
            @Field(Constants.REDIRECT_URI_KEY) String redirect_uri,
            @Field(Constants.SINGLE_CHANNEL_KEY) boolean single_channel

    );

    @GET("channels.list")
    Call<channelListModel> getChannelInfo(
            @Query("token") String token
    );

    @GET("rtm.connect")
    Call<rtmConnectModel> checkRtmSuccess(
            @Query("token") String token
    );

    @GET("channels.history")
    Call<channelHistoryModel> channelHistory(
            @Query("token") String token,
            @Query("channel") String channel
    );

    @GET("users.info")
    Call<userInfoModel> userDetails(
            @Query("token") String token,
            @Query("user") String user
    );

}
