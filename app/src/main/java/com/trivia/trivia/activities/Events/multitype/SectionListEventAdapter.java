package com.trivia.trivia.activities.Events.multitype;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Events.DialogEventEnd;
import com.trivia.trivia.activities.Game.GameSellQuestion.sellOkDialog;
import com.trivia.trivia.activities.Messages.GlideApp;
import com.trivia.trivia.util.Event;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class SectionListEventAdapter extends RecyclerView.Adapter<SectionListEventAdapter.SingleItemRowHolder2> {
    OnCardClickListner onCardClickListner;

    private ArrayList<Event> itemModels;
    private Context mContext;

    public SectionListEventAdapter(ArrayList<Event> itemModels, Context mContext) {
        this.itemModels = itemModels;
        this.mContext = mContext;
    }

    @Override
    public SingleItemRowHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_events_unregistered, null);
        SectionListEventAdapter.SingleItemRowHolder2 singleItemRowHolder = new SectionListEventAdapter.SingleItemRowHolder2(v);
        return singleItemRowHolder;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder2 holder, int position) {
        Event itemModel = itemModels.get(position);
        holder.event_name_tv.setText(itemModel.getE_name());
        holder.event_tv_type.setText(itemModel.getE_type());
        try {
            PersianDate persianDate=new PersianDate(Long.parseLong(itemModel.getE_start_time())*1000);
            holder.event_tv_date.setText(persianDate.getShDay()+" "+persianDate.monthName());
        }catch (Exception e){
            PersianDate persianDate=new PersianDate(Long.parseLong(itemModel.getE_gather_start_time())*1000);
            holder.event_tv_date.setText(persianDate.getShDay()+" "+persianDate.monthName());

        }

        holder.event_tv_cost.setText(itemModel.getE_price()+" "+"تومان");


        if(itemModel.getE_type().equals("گردهمایی")){
            holder.event_tv_type.setBackgroundResource(R.drawable.corner_blue);
            holder.event_tv_member.setText(itemModel.getE_city());}
        else { holder.event_tv_member.setText("آنلاین");
            holder.event_tv_type.setBackgroundResource(R.drawable.corner_yellow);}

        //  holder.itemImage.setImageResource(array_image_place[1]);
       // Glide.with(mContext).load(getImage("back_blue")).into(holder.itemImage);


        GlideApp.with(mContext)
                .asBitmap()
                .load(itemModel.getE_img())
                .diskCacheStrategy(DiskCacheStrategy.ALL)

                .into(holder.itemImage);
        holder.item_event_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClickListner.OnCardClicked(view, itemModel);
            }
        });

        Calendar cal=Calendar.getInstance();
        if (Integer.parseInt(itemModel.getE_reg_deadline())<cal.getTimeInMillis()/1000) {


            holder.iv.setVisibility(View.VISIBLE);
            holder.item_event_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog ad;
                    DialogEventEnd dt = new DialogEventEnd();
                    ad = dt.init(mContext,"مهلت ثبت نام در رویداد به پایان رسیده است.",itemModel.getE_id());
                    Button btn_ok_alert = dt.getview().findViewById(R.id.event_detail_btn);
                    btn_ok_alert.setVisibility(View.GONE);

                    ad.show();
                }
            });


        }else {
            holder.iv.setVisibility(View.GONE);
            holder.item_event_card.setClickable(true);
            holder.item_event_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onCardClickListner.OnCardClicked(view, itemModel);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != itemModels ? itemModels.size() : 0);
    }

    public class SingleItemRowHolder2 extends RecyclerView.ViewHolder {

        protected TextView event_name_tv, event_tv_type, event_tv_cost, event_tv_date,event_tv_member;
        protected ImageView itemImage;
        protected CardView item_event_card;
        ImageView iv;

        public SingleItemRowHolder2(View itemView) {
            super(itemView);
            iv= itemView.findViewById(R.id.txt_message);
            this.event_name_tv = itemView.findViewById(R.id.event_tv_name_2);
            this.event_tv_member = itemView.findViewById(R.id.event_tv_member);
            this.event_tv_type = itemView.findViewById(R.id.event_tv_type);
            this.event_tv_cost = itemView.findViewById(R.id.event_tv_cost);
            this.event_tv_date = itemView.findViewById(R.id.event_tv_date);
            this.itemImage = itemView.findViewById(R.id.event_iv_main);
            this.item_event_card = itemView.findViewById(R.id.item_event_card);
            //  Typeface typeface = itemView.getResources().getFont(R.font.iran_sans_mobile);
            //  tvPlace.setTypeface(typeface, Typeface.BOLD);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(view.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public int getImage(String imageName) {

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getApplicationContext().getPackageName());

        return drawableResourceId;
    }
    public interface OnCardClickListner {
        void OnCardClicked(View view, Event position);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }
}
