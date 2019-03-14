package com.trivia.trivia.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trivia.trivia.R;
import com.trivia.trivia.util.NewsArticle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_my_activity_recycle extends RecyclerView.Adapter<adapter_my_activity_recycle.MyViewHolder> {
    private List<NewsArticle> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;
    Spannable word;
    Spannable wordTwo;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView card_title;
        // TextView card_info;
        TextView card_date;

        TextView textDescription4;
        ImageView iv_title;
        public LinearLayout CV;

        public MyViewHolder(View view) {
            super(view);
            card_title = (TextView) view.findViewById(R.id.title);
            // card_info = (TextView) view.findViewById(R.id.card_info);
            //  card_date = (TextView) view.findViewById(R.id.card_date);
            CV = (LinearLayout) view.findViewById(R.id.main_lay);

            iv_title = (ImageView) view.findViewById(R.id.imageView_news);
            textDescription4 = (TextView) view.findViewById(R.id.textDescription4);
            // Toast.makeText(view.getContext(),"تست", Toast.LENGTH_SHORT).show();
        }
    }


    public adapter_my_activity_recycle(ArrayList<NewsArticle> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activiy, parent, false);
        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final NewsArticle data_service = data_services_list.get(position);
        // String s = data_service.getAnswer().replace("ss", "");
        holder.card_title.setText(data_service.getTitle());

        setTextDesc( holder.textDescription4, "علی"," شما را به گروه"," ایرانی ها"," دعوت کرد.");

       /* SpannableStringBuilder str = new SpannableStringBuilder(data_service.getTitle());
        str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, str.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.card_title.setText(str);
        //holder.card_info.setText(s);
        */
      /*  if (data_service.getCode() == 0) {
            holder.CV.setBackgroundColor(Color.parseColor("#d8d9d8"));
        }
        */
        //  Glide.with(context).load(data_service.getUrlToImage()).into(holder.iv_title);

      //  new DownloadImageTask((ImageView) holder.iv_title).execute(data_service.getUrlToImage());

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
    private void setTextDesc(TextView textView, String text1, String text2, String text3, String text4){
        word = new SpannableString(text1);
        word.setSpan(new StyleSpan(Typeface.BOLD), 0, word.length(), 0);
        textView.setText(word);

        wordTwo = new SpannableString(text2);
        wordTwo.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.grey_600)), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(wordTwo);

        word = new SpannableString(text3);
        word.setSpan(new StyleSpan(Typeface.BOLD), 0, word.length(), 0);
        textView.append(word);

        wordTwo = new SpannableString(text4);
        wordTwo.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.grey_600)), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(wordTwo);
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