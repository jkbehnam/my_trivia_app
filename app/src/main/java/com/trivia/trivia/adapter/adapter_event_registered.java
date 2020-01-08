package com.trivia.trivia.adapter;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.codesgood.views.JustifiedTextView;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Events.DialogEventEnd;
import com.trivia.trivia.activities.Messages.GlideApp;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.list_item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import saman.zamani.persiandate.PersianDate;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_event_registered extends RecyclerView.Adapter<adapter_event_registered.MyViewHolder> {
    private String p_code;
    private List<Event> data_event_list;

    Context context1;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_event_tv_main)
        JustifiedTextView tv_main;
        @BindView(R.id.item_event_iv_main)
        ImageView iv_main;
        @BindView(R.id.event_list_rcle)
        RecyclerView rv_event;
        @BindView(R.id.item_event_card)
        CardView cv_event;
        @BindView(R.id.txt_message)
        ImageView iv;
@BindView(R.id.iv_event_type)
ImageView iv_event_type;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


    public adapter_event_registered(ArrayList<Event> data_services_list) {
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

        GridLayoutManager layoutManager = new GridLayoutManager(context1, 3);
        holder.rv_event.setLayoutManager(layoutManager);
        ArrayList<list_item> uel = new ArrayList<list_item>();
        uel.add(new list_item(data_event.getE_type(), "game", "نوع رویداد"));
        if (data_event.getE_type().equals("مسابقه")){
            uel.add(new list_item("آنلاین", "placeholder", "محل برگزاری"));
            holder.iv_event_type.setColorFilter(ContextCompat.getColor(context1, R.color.event_main_title), android.graphics.PorterDuff.Mode.MULTIPLY);
        }else {
            uel.add(new list_item(data_event.getE_city(), "placeholder", "محل برگزاری"));
            holder.iv_event_type.setColorFilter(ContextCompat.getColor(context1, R.color.boxStrokeColor), android.graphics.PorterDuff.Mode.MULTIPLY);

        }

        PersianDate persianDate;
        try {
             persianDate=new PersianDate(Long.parseLong(data_event.getE_start_time())*1000);
            uel.add(new list_item(persianDate.getShDay()+" "+persianDate.monthName(), "placeholder", "محل برگزاری"));
        }catch (Exception e){

            persianDate=new PersianDate(Long.parseLong(data_event.getE_gather_start_time())*1000);
            uel.add(new list_item(persianDate.getShDay()+" "+persianDate.monthName(), "placeholder", "محل برگزاری"));
        }



        adapter_event_items_main madapter = new adapter_event_items_main(uel);
//        Calendar cal=Calendar.getInstance();
//
        if (!data_event.getG_end_time().equals("0")) {
            holder.iv.setVisibility(View.VISIBLE);
            holder.cv_event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog ad;
                    DialogEventEnd dt = new DialogEventEnd();
                    ad = dt.init(context1,"رویداد به پایان رسیده است.",data_event.getE_id());

                    ad.show();
                }
            });



        }else {
            holder.iv.setVisibility(View.GONE);
            holder.cv_event.setClickable(true);
            holder.cv_event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onCardClickListner.OnCardClicked(view, data_event);
                }
            });
        }
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        holder.rv_event.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapter_event_items_main.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

            }
        });
     //   Glide.with(context1).load(data_event.getE_img()).into(holder.iv_main);
        GlideApp.with(context1)
                .asBitmap()
                .load(data_event.getE_img())

                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_main);

    }

    @Override
    public int getItemCount() {
        return data_event_list.size();
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view , Event event);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

}