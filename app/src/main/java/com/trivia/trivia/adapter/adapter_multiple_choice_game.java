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
     /*   public TextView card_title;
        public CheckBox ch;
        public CardView CV;
        ImageView info;
*/
     Button btn_q;
        public MyViewHolder(View view) {
            super(view);
         /*   card_title = (TextView) view.findViewById(R.id.textView5);
            ch = (CheckBox) view.findViewById(R.id.checkBox);
            info = (ImageView) view.findViewById(R.id.iv_info);
            // card_info = (TextViewEx) view.findViewById(R.id.home_card_info);
            CV = (CardView) view.findViewById(R.id.exer_cv);
            // Toast.makeText(view.getContext(),"تست", Toast.LENGTH_SHORT).show();
*/
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
/*
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation_alertdialog aa = new animation_alertdialog();
                if (Type.equals("warmup")) {

                    aa.qrcode_reader(context, "warm_up",position);
                } else {
                    aa.qrcode_reader(context, "cold_down",position);
                }
                aa.show();

            }
        });
        holder.card_title.setText(data_service.getTitle());
        holder.ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    data_service.setIs_checked(1);
                } else data_service.setIs_checked(0);
            }
        });
        holder.CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.ch.isChecked()) {
                    holder.ch.setChecked(true);

                } else {
                    holder.ch.setChecked(false);


                }
            }
        });
*/

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