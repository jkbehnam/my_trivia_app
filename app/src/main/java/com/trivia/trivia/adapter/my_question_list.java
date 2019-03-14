package com.trivia.trivia.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trivia.trivia.R;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.util.list_item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class my_question_list extends RecyclerView.Adapter<my_question_list.MyViewHolder> {
    private String p_code;
    private List<Question> data_event_list;

    Context context1;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_event_tv_main)
        TextView tv_main;
        @BindView(R.id.event_list_rcle)
        RecyclerView rv_event;
        @BindView(R.id.item_event_card)
        CardView cv_event;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


    public my_question_list(ArrayList<Question> data_services_list) {
        this.data_event_list = data_services_list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_myquestions, parent, false);
        context1 = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Question data_event = data_event_list.get(position);

        holder.tv_main.setText(data_event.getQ_title());

        GridLayoutManager layoutManager = new GridLayoutManager(context1, 2);
        holder.rv_event.setLayoutManager(layoutManager);
        ArrayList<list_item> uel=new ArrayList<list_item>();
        uel.add(new list_item(data_event.getQ_score(),"effort", "امتیاز" ,0) );
        uel.add(new list_item(data_event.getQ_type(),"question","نوع سوال",Integer.parseInt(data_event.getQ_level())));
        adapter_list_event_unreg madapter = new adapter_list_event_unreg(uel);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        holder. rv_event.setAdapter(madapter);


      //  Glide.with(context1).load(data_event.getQ_img()).into(holder.iv_main);
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