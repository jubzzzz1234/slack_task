package com.example.slack_task.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.slack_task.Constants;
import com.example.slack_task.R;
import com.example.slack_task.models.channelHistoryModel;
import com.example.slack_task.models.userInfoModel;
import com.example.slack_task.retrofit.GetDataService;
import com.example.slack_task.retrofit.RetrofitClientInstance;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class channelHistoryAdapter extends RecyclerView.Adapter<channelHistoryAdapter.MyViewHolder2> {

    List<channelHistoryModel.messageModel> channelHistoryList;
    Context context;
    private userInfoModel userModel;
    SharedPreferences sharedPreferences;

    public channelHistoryAdapter(List<channelHistoryModel.messageModel> channelHistoryList, Context context) {
        this.channelHistoryList = channelHistoryList;
        this.context = context;
    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.channel_rv_layout, parent, false);

        return new channelHistoryAdapter.MyViewHolder2(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder2 holder, int position) {
        channelHistoryModel.messageModel messageModel=channelHistoryList.get(position);
        holder.txt_message.setText(messageModel.getText());


        sharedPreferences=context.getSharedPreferences(Constants.NAME,Context.MODE_PRIVATE);


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<userInfoModel> userModelCall = service.userDetails(sharedPreferences.getString(Constants.ACCESS_TOKEN,""),
                messageModel.getUser()
        );
        userModelCall.enqueue(new Callback<userInfoModel>() {
            @Override
            public void onResponse(@NonNull Call<userInfoModel> call, @NonNull Response<userInfoModel> response) {

                if (response.isSuccessful()) {

                    userModel = response.body();
                    assert userModel != null;
                    Log.e("USER MODEL +++=========",new Gson().toJson(userModel));
                    holder.progressBar.setVisibility(View.GONE);
                    holder.txt_user_name.setVisibility(View.VISIBLE);

//                    if (!userModel.getUser().getName().isEmpty()) {

                    if(userModel.isOk())
                    {
                        holder.txt_user_name.setText(userModel.getUser().getName());
                    }
                    else
                    {
                        holder.txt_user_name.setText("");
                    }



                }
            }

            @Override
            public void onFailure(@NonNull Call<userInfoModel> call, @NonNull Throwable t) {

            }
        });
    }



    @Override
    public int getItemCount() {
        return channelHistoryList.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_user_name)
        TextView txt_user_name;
        @BindView(R.id.txt_message)
        TextView txt_message;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;


        public MyViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

}
