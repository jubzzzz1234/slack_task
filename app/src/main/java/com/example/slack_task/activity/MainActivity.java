package com.example.slack_task.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.slack_task.Constants;
import com.example.slack_task.R;
import com.example.slack_task.adapters.channelAdapter;
import com.example.slack_task.models.channelListModel;
import com.example.slack_task.models.tokenModel;
import com.example.slack_task.retrofit.GetDataService;
import com.example.slack_task.retrofit.RetrofitClientInstance;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    SharedPreferences sharedPreferences;
    public static final int REQUEST_CODE = 111;

    private String code;
    private tokenModel token_model;
    private channelListModel channel_list_model;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sharedPreferences=getSharedPreferences(Constants.NAME, Context.MODE_PRIVATE);


        if(sharedPreferences.getString(Constants.ACCESS_TOKEN,"").equals(""))
        {
            /*Intent intent = new Intent(MainActivity.this, webActivity.class);
            startActivity(intent);
            finish();*/
            Intent intent = new Intent(MainActivity.this, webActivity.class);
            startActivityForResult(intent,REQUEST_CODE);
        }
        else
        {
            layout_view();
        }




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {
               // assert data != null;
                code = data.getStringExtra("code");
                Log.e("code_home", code);
                if (sharedPreferences.getString(Constants.ACCESS_TOKEN,"").equals("")) {

                    getToken();
                } else {

                    layout_view();
                }

            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    private void layout_view() {

        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);



        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<channelListModel> channelCall = service.getChannelInfo(sharedPreferences.getString(Constants.ACCESS_TOKEN,""));
        channelCall.enqueue(new Callback<channelListModel>() {
            @Override
            public void onResponse(@NonNull Call<channelListModel> call, @NonNull Response<channelListModel> response) {

                if (response.isSuccessful()) {

                    channel_list_model = response.body();
                    Log.e("channelInfoModel", new Gson().toJson(channel_list_model));
                    channelLayout();
                }
            }

            @Override
            public void onFailure(@NonNull Call<channelListModel> call, @NonNull Throwable t) {

            }
        });

    }

    private void channelLayout() {
        channelAdapter channelListAdapter = new channelAdapter(channel_list_model.channelDetails, MainActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(channelListAdapter);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void getToken() {


        Log.e("getToken()","getToken()");
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        final Call<tokenModel> accessTokenCall = service.getAccessToken(
                Constants.CLIENT_ID,
                Constants.CLIENT_SECRET,
                code,
                Constants.REDIRECT_URI,
                true
        );

        accessTokenCall.enqueue(new Callback<tokenModel>() {
            @Override
            public void onResponse(@NonNull Call<tokenModel> call, @NonNull Response<tokenModel> response) {

                if (response.isSuccessful()) {

                    Log.e("response_code", String.valueOf(response.code()));
                    Log.e("response_body", new Gson().toJson(response.body()));
                    token_model = response.body();
//                    assert accessTokenModel != null;
                    Log.e("access_token_is",token_model.getAccess_token());

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(Constants.ACCESS_TOKEN,token_model.getAccess_token());
                    editor.putString(Constants.USER_ID,token_model.getUser_id());
                    editor.apply();

                    layout_view();
                }



            }


            @Override
            public void onFailure(@NonNull Call<tokenModel> call, @NonNull Throwable t) {

            }
        });
    }
}
