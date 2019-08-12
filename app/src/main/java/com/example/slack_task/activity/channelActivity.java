package com.example.slack_task.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.slack_task.Constants;
import com.example.slack_task.R;
import com.example.slack_task.adapters.channelHistoryAdapter;
import com.example.slack_task.models.MessageModel;
import com.example.slack_task.models.MessageRecieveModel;
import com.example.slack_task.models.channelHistoryModel;
import com.example.slack_task.models.rtmConnectModel;
import com.example.slack_task.retrofit.GetDataService;
import com.example.slack_task.retrofit.RetrofitClientInstance;
import com.google.gson.Gson;

import java.util.concurrent.ThreadLocalRandom;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class channelActivity extends AppCompatActivity {

    private rtmConnectModel rtmModel;
    private String channel;
    private String channel_name;
    private channelHistoryModel channelHistoryModel;
    private channelHistoryAdapter channelAdapter;
    private WebSocket webSocket;
    private NotificationReceiver Receiver;
    private String notification_text;



    @BindView(R.id.channel_recyclerView)
    RecyclerView channel_recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txt_channel_name)
    TextView txt_channel_name;
    @BindView(R.id.edttxt_msg_box)
    EditText edttxt_msg_box;
    @BindView(R.id.img_send)
    ImageView img_send;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        ButterKnife.bind(this);

        sharedPreferences=getSharedPreferences(Constants.NAME,Context.MODE_PRIVATE);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            channel = bundle.getString("channel_id");
            channel_name = bundle.getString("channel_name");
            txt_channel_name.setText(channel_name);
            channelHistory();

        }

        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int randomNum = ThreadLocalRandom.current().nextInt(1, 1000 + 1);
                Log.e("RANDOM_________",""+randomNum);
               sendMessage(randomNum);

            }
        });

        channelHistory();

        Receiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BROADCAST_EXTRA);
        registerReceiver(Receiver, filter);

        checkRtmStatus();

    }

    private void sendMessage(int i) {
        MessageModel messageModel = new MessageModel();
        messageModel.setId(i);
        messageModel.setChannel(channel);
        messageModel.setType(Constants.MESSAGE_TYPE_MESSAGE);
        messageModel.setText(edttxt_msg_box.getText().toString());
        edttxt_msg_box.getText().clear();
        Log.e("MESSAGE",new Gson().toJson(messageModel));
        webSocket.send(new Gson().toJson(messageModel));
        checkRtmStatus();
    }

    private void channelHistory() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<channelHistoryModel> channelHistoryModelCall = service.channelHistory(sharedPreferences.getString(Constants.ACCESS_TOKEN,""),
                channel);
        channelHistoryModelCall.enqueue(new Callback<channelHistoryModel>() {
            @Override
            public void onResponse(Call<channelHistoryModel> call, Response<channelHistoryModel> response) {

                if (response.isSuccessful()) {

                    channelHistoryModel = response.body();
                    Log.e("channelHistoryModel",new Gson().toJson(channelHistoryModel));

                    Log.e("getMessageFromChannel()",new Gson().toJson(channelHistoryModel.getMessageFromChannel()));

                    channelAdapter = new channelHistoryAdapter(channelHistoryModel.getMessageFromChannel(), channelActivity.this);
                    channel_recyclerView.setHasFixedSize(true);
                    channel_recyclerView.setAdapter(channelAdapter);
                    progressBar.setVisibility(View.GONE);
                    channel_recyclerView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<channelHistoryModel> call, Throwable t) {

            }
        });

    }


    private void checkRtmStatus() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<rtmConnectModel> checkRtmSuccess = service.checkRtmSuccess(sharedPreferences.getString(Constants.ACCESS_TOKEN,""));
        checkRtmSuccess.enqueue(new Callback<rtmConnectModel>() {
            @Override
            public void onResponse(Call<rtmConnectModel> call, Response<rtmConnectModel> response) {

                if (response.isSuccessful()) {

                    rtmModel = response.body();
                    Log.e("rtmBaseModel", new Gson().toJson(rtmModel));
                    connectRtmServer();

                }
            }

            @Override
            public void onFailure(Call<rtmConnectModel> call, Throwable t) {

            }
        });
    }

    private void connectRtmServer() {


        webSocket = new OkHttpClient().newWebSocket(new Request.Builder().url(rtmModel.getUrl()).build(), new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                super.onOpen(webSocket, response);
                Log.e("onOpen_response", new Gson().toJson(response));
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                Log.e("onMessage", text);
                MessageRecieveModel messageModel = new Gson().fromJson(text, MessageRecieveModel.class);
                Log.e("messageModel", new Gson().toJson(messageModel));
                if (messageModel.getType().equals(Constants.MESSAGE_TYPE_MESSAGE)) {

                    channelHistory();
                    if (!sharedPreferences.getString(Constants.USER_ID,"").equals(messageModel.getUser())) {

                        notification_text = messageModel.getText();
                        Intent i = new Intent(Constants.BROADCAST_EXTRA);
                        i.putExtra("broadcast", "msg");
                        sendBroadcast(i);
                    }


                }


            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                Log.e("onClosed", reason);
            }
        });


    }

    class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            notification();
        }
    }

    private void notification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Slack Test Notification")
                        .setContentText(notification_text);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webSocket.cancel();
        unregisterReceiver(Receiver);

    }


}
