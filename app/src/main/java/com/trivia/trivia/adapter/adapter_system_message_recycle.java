package com.trivia.trivia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.trivia.trivia.R;
import com.trivia.trivia.util.DownloadImageTask;
import com.trivia.trivia.util.NewsArticle;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import saman.zamani.persiandate.PersianDate;

/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_system_message_recycle extends RecyclerView.Adapter<adapter_system_message_recycle.MyViewHolder> {
    private List<NewsArticle> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView card_title;
       // TextView card_info;
        TextView time,author;
ImageView iv_title;
        public FrameLayout CV;

        public MyViewHolder(View view) {
            super(view);
            card_title = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            author = (TextView) view.findViewById(R.id.author);
            CV = (FrameLayout) view.findViewById(R.id.main_lay);
            iv_title=(ImageView)view.findViewById(R.id.imageView_news);
            // Toast.makeText(view.getContext(),"تست", Toast.LENGTH_SHORT).show();
        }
    }


    public adapter_system_message_recycle(ArrayList<NewsArticle> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news4, parent, false);
        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final NewsArticle data_service = data_services_list.get(position);
       // String s = data_service.getAnswer().replace("ss", "");
        holder.card_title.setText(data_service.getTitle());
       /* SpannableStringBuilder str = new SpannableStringBuilder(data_service.getTitle());
        str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, str.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.card_title.setText(str);
        //holder.card_info.setText(s);
        */
      /*  if (data_service.getCode() == 0) {
            holder.CV.setBackgroundColor(Color.parseColor("#d8d9d8"));
        }
        */
       // Glide.with(context).load("http://blackfish5.org/img/cognitive.png").into(holder.iv_title);
        //Calendar c=Calendar.getInstance();
        PersianDate p=new PersianDate(Long.parseLong(data_service.getPublishedAt())*1000);
        holder.time.setText(p.toString());
        holder.author.setText(data_service.getAuthor());
        new DownloadImageTask((ImageView) holder.iv_title)
                .execute(data_service.getUrlToImage_small());

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