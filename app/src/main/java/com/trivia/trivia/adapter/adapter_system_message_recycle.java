package com.trivia.trivia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.trivia.trivia.R;
import com.trivia.trivia.util.Daily_system_massage;
import com.trivia.trivia.util.NewsArticle;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_system_message_recycle extends RecyclerView.Adapter<adapter_system_message_recycle.MyViewHolder> {
    private List<NewsArticle> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public JustifiedTextView card_title;
       // TextView card_info;
        TextView card_date;
ImageView iv_title;
        public CardView CV;

        public MyViewHolder(View view) {
            super(view);
            card_title = (JustifiedTextView) view.findViewById(R.id.tv_news_title);
           // card_info = (TextView) view.findViewById(R.id.card_info);
            //  card_date = (TextView) view.findViewById(R.id.card_date);
            CV = (CardView) view.findViewById(R.id.cardview_be_intelligent);
            iv_title=(ImageView)view.findViewById(R.id.iv_news_image);
            // Toast.makeText(view.getContext(),"تست", Toast.LENGTH_SHORT).show();


        }
    }


    public adapter_system_message_recycle(ArrayList<NewsArticle> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aanews_item, parent, false);
        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final NewsArticle data_service = data_services_list.get(position);
       // String s = data_service.getAnswer().replace("ss", "");
        holder.card_title.setText(data_service.getTitle());

        //holder.card_info.setText(s);
      /*  if (data_service.getCode() == 0) {
            holder.CV.setBackgroundColor(Color.parseColor("#d8d9d8"));
        }
        */
        Glide.with(context).load(data_service.getUrlToImage()).into(holder.iv_title);

        holder.CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnCardClicked(v, position);
            }
        });
        holder.card_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClickListner.OnCardClicked(view, position);
            }
        });

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