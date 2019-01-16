package com.trivia.trivia.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trivia.trivia.R;
import com.trivia.trivia.util.Event;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class eventList_registered extends RecyclerView.Adapter<eventList_registered.MyViewHolder> {
    private String p_code;
    private List<Event> data_event_list;

    Context context1;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_event_tv_main)
        TextView tv_main;
        @BindView(R.id.item_event_iv_main)
        ImageView iv_main;
        @BindView(R.id.item_event_iv_1)
        ImageView iv_1;
        @BindView(R.id.item_event_iv_2)
        ImageView iv_2;
        @BindView(R.id.item_event_tv_1)
        TextView tv_1;
        @BindView(R.id.item_event_tv_2)
        TextView tv_2;
        @BindView(R.id.item_event_card)
        CardView cv_event;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


    public eventList_registered(ArrayList<Event> data_services_list) {
        this.data_event_list = data_services_list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_registered_events, parent, false);
        context1 = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Event data_event = data_event_list.get(position);

        holder.tv_main.setText(data_event.getE_name());
        holder.tv_1.setText(data_event.getE_province());
        holder.tv_2.setText(data_event.getE_start_time());
        Glide.with(context1).load(data_event.getE_img()).into(holder.iv_main);
        holder.cv_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onCardClickListner.OnCardClicked(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data_event_list.size();
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

}