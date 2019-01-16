package com.trivia.trivia.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.trivia.trivia.R;
import com.trivia.trivia.util.MultipleChoiceItems;
import com.trivia.trivia.util.MultipleChoicePage;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_multiple_choice_game extends RecyclerView.Adapter<adapter_multiple_choice_game.MyViewHolder> {
    private List<MultipleChoiceItems> data_services_list;
    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
     Button btn_q;
        public MyViewHolder(View view) {
            super(view);

         btn_q=(Button)view.findViewById(R.id.btn_multiple_choice);

        }
    }


    public adapter_multiple_choice_game(ArrayList<MultipleChoiceItems> data_services_list) {
        this.data_services_list = data_services_list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view_multiple_choice, parent, false);

        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }



    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MultipleChoiceItems data_service = data_services_list.get(position);
        holder.btn_q.setText(data_service.getTitle());
        holder.btn_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnCardClicked(v, position);
            }
        });
        switch (data_service.getState()){
            case 0:

                holder.btn_q.setBackgroundResource(R.drawable.button_question_unselected_background);
                break;
            case 1:
                holder.btn_q.setBackgroundResource(R.drawable.button_question_grean_background);
                break;
            case 2:
                holder.btn_q.setBackgroundResource(R.drawable.button_question_red_background);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return data_services_list.size();
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

}