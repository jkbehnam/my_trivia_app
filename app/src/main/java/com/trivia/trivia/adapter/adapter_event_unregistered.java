package com.trivia.trivia.adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trivia.trivia.R;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.list_item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import saman.zamani.persiandate.PersianDate;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_event_unregistered extends RecyclerView.Adapter<adapter_event_unregistered.MyViewHolder> {
    private String p_code;
    private List<Event> data_event_list;

    Context context1;
    OnRegBtnClickListner onRegBtnClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_event_tv_main)
        TextView tv_main;
        @BindView(R.id.item_event_iv_main)
        ImageView iv_main;
        @BindView(R.id.event_list_rcle)
        RecyclerView rv_event;
        @BindView(R.id.item_event_card)
        CardView cv_event;
        @BindView(R.id.item_event_reg_btn)
        Button btnReg;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public void setData(ArrayList<Event> data_services_list) {
        this.data_event_list = data_services_list;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<Event> data_services_list) {
        this.data_event_list.addAll(data_services_list);
        notifyDataSetChanged();
    }
    public adapter_event_unregistered(ArrayList<Event> data_services_list) {
        this.data_event_list = data_services_list;

    }
    public adapter_event_unregistered() {
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unregistered_events, parent, false);
        context1 = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Event data_event = data_event_list.get(position);

        holder.tv_main.setText(data_event.getE_name());

        GridLayoutManager layoutManager = new GridLayoutManager(context1, 2);
        holder.rv_event.setLayoutManager(layoutManager);
        ArrayList<list_item> uel = new ArrayList<list_item>();
        uel.add(new list_item(data_event.getE_type(), "game", "نوع رویداد"));
        uel.add(new list_item(data_event.getE_price()+" ريال", "coin", "هزینه"));
        uel.add(new list_item(data_event.getE_city(), "placeholder", "محل برگزاری"));
        if(!data_event.getE_duration().equals("0")){
        uel.add(new list_item(data_event.getE_duration()+" ساعت", "clock", "مدت زمان"));}
        PersianDate pd=new PersianDate(Long.parseLong(data_event.getE_start_time())*1000);
        String date= pd.getShYear() + "/" + pd.getShMonth() + "/" + pd.getShDay();
        String date2= pd.getHour() + ":" + pd.getMinute();
        uel.add(new list_item("دلخواه", "clock", "زمان شروع"));
       // uel.add(new list_item(date2, "clock", "ساعت شروع"));

        adapter_event_items madapter = new adapter_event_items(uel);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        holder.rv_event.setAdapter(madapter);
        madapter.setOnCardClickListner(new adapter_event_items.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

            }
        });

        Glide.with(context1).load(data_event.getE_img()).into(holder.iv_main);
        holder.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegBtnClickListner.OnCardClicked(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data_event_list.size();
    }

    public interface OnRegBtnClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnRegBtnClickListner(OnRegBtnClickListner onRegBtnClickListner) {
        this.onRegBtnClickListner = onRegBtnClickListner;
    }

}