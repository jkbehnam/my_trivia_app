package com.trivia.trivia.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;


import com.trivia.trivia.R;
import com.trivia.trivia.util.Question;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapterAnswerList extends RecyclerView.Adapter<adapterAnswerList.MyViewHolder> {
    private String p_code;
    private List<Question> data_services_list;

    Context context1;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView q_name;



        public MyViewHolder(View view) {
            super(view);
            q_name = (TextView) view.findViewById(R.id.tv_name);

        }
    }


    public adapterAnswerList(ArrayList<Question> data_services_list) {
        this.data_services_list = data_services_list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_accept_answer, parent, false);
        context1 = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Question data_service = data_services_list.get(position);

        holder.q_name.setText(data_service.getQ_title());



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