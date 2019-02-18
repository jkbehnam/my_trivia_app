package com.trivia.trivia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.trivia.trivia.R;
import com.trivia.trivia.util.OtherGamer;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_gamers_list extends RecyclerView.Adapter<adapter_gamers_list.MyViewHolder> {
    private String p_code;
    private List<OtherGamer> data_services_list;

    Context context1;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView q_name, q_code, q_level, q_get_time;
        LinearLayout li;


        public MyViewHolder(View view) {
            super(view);
            q_name = (TextView) view.findViewById(R.id.tv_name);
            q_code = (TextView) view.findViewById(R.id.tv_member);
            q_level = (TextView) view.findViewById(R.id.tv_score);
            q_get_time = (TextView) view.findViewById(R.id.tv_time);
            q_get_time.setVisibility(View.GONE);
            li = (LinearLayout) view.findViewById(R.id.relativeLayout1);
        }
    }


    public adapter_gamers_list(ArrayList<OtherGamer> data_services_list, String p_code) {
        this.data_services_list = data_services_list;
        this.p_code = p_code;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gamers_list, parent, false);
        context1 = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final OtherGamer data_service = data_services_list.get(position);
        if (position == 0)
            holder.li.setBackgroundColor(context1.getResources().getColor(R.color.colorPrimaryDark));
        holder.q_name.setText(data_service.getName());
        holder.q_code.setText(data_service.getMembers());
        holder.q_level.setText(String.valueOf(data_service.getScore()));
        holder.q_get_time.setText(String.valueOf(data_service.getAnswerd_q()));


    }

    @Override
    public int getItemCount() {
        return data_services_list.size();
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }
}