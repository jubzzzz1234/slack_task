package com.example.slack_task.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slack_task.R;
import com.example.slack_task.activity.channelActivity;
import com.example.slack_task.models.channelListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

public class channelAdapter extends RecyclerView.Adapter<channelAdapter.MyViewHolder> {

    List<channelListModel.ChannelDetails> channelDetailsList;
    Context context;

    public channelAdapter(List<channelListModel.ChannelDetails> channelDetailsList, Context context) {
        this.channelDetailsList = channelDetailsList;
        this.context = context;
    }

    @Override
    public channelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.channel_layout, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(channelAdapter.MyViewHolder holder, int position) {
        final channelListModel.ChannelDetails channelmodel=channelDetailsList.get(position);
        holder.txt_channel_name.setText(channelmodel.getName());
        holder.txt_channel_desc.setText(channelmodel.getPurpose().getValue());

        holder.channel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, channelActivity.class);
                intent.putExtra("channel_id", channelmodel.getId());
                intent.putExtra("channel_name", channelmodel.getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return channelDetailsList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.channel_name)
        TextView txt_channel_name;
        @BindView(R.id.channel_desc)
        TextView txt_channel_desc;
        @BindView(R.id.channel_layout)
        LinearLayout channel_layout;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

}
