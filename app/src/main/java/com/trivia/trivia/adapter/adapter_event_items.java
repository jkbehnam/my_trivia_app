package com.trivia.trivia.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.trivia.trivia.R;
import com.trivia.trivia.util.list_item;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_event_items extends RecyclerView.Adapter<adapter_event_items.MyViewHolder> {
    private List<list_item> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView card_title;
        public TextView card_name;
        public ImageView img;
        public CardView cv;
        public LinearLayout layout;


        public MyViewHolder(View view) {
            super(view);
            card_title = view.findViewById(R.id.item_event_tv_2);
            card_name = view.findViewById(R.id.item_event_tv_3);
            img = view.findViewById(R.id.item_event_iv_2);
            cv = view.findViewById(R.id.item_event_card);
            layout = view.findViewById(R.id.lay);
        }
    }


    public adapter_event_items(ArrayList<list_item> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question_list, parent, false);

        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final list_item data_service = data_services_list.get(position);
        holder.card_title.setText(data_service.getContent());
        holder.card_name.setText(data_service.getName());
        int color = 0;
      //  holder.img.setImageResource(R.drawable.clock);
       // Glide.with(context).load("android.resource://" + com.example.behnam.myapplication.activities.MainActivity.PACKAGE_NAME + "/" + String.valueOf(data_service.getImg())).into(holder.img);

        Glide.with(context).load(getImage(data_service.getImg())).into(holder.img);
       /*
        switch (data_service.getColor()){
            case 1:
                color=R.drawable.lay_stock_green;
                holder.img.setColorFilter(ContextCompat.getColor(context, R.color.q_color_green), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 2:
                color=R.drawable.lay_stock_green;
                holder.img.setColorFilter(ContextCompat.getColor(context, R.color.q_color_yellow), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 3:
                color=R.drawable.lay_stock_green;
                holder.img.setColorFilter(ContextCompat.getColor(context, R.color.q_color_red), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
        }
        */
      /*  if(color!=0){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            holder. layout.setBackgroundDrawable(ContextCompat.getDrawable(context, color) );
        } else {
            holder. layout.setBackground(ContextCompat.getDrawable(context,color));
        }}*/

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
    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }
}