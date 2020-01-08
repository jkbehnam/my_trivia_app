package com.trivia.trivia.activities.Events.multitype;

import android.content.Context;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trivia.trivia.R;


import java.util.ArrayList;

public class SectionVideoListDataAdapter extends RecyclerView.Adapter<SectionVideoListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemModels;
    private Context mContext;
    private static String[] array_image_place = {
            "#5B2677",
            "#133A5E",
            "#E60023",
            "#B469EB",
            "#E3780C",



    };
    private static String[] array_image_place2 = {
            "#EA514D",
            "#8DC7CB",
            "#635554",
            "#BBA399",
            "#E3780C",



    };
    private static String[] array_title_place = {
            "گسسته",
            "مثلثات",
            "الگوریتم",
            "شی گرایی",
            "عید کرمان",
            "عید کرمان",
            "عید کرمان",
            "عید کرمان",


    };


    public SectionVideoListDataAdapter(ArrayList<SingleItemModel> itemModels, Context mContext) {
        this.itemModels = itemModels;
        this.mContext = mContext;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_recent, null);
        SectionVideoListDataAdapter.SingleItemRowHolder singleItemRowHolder = new SectionVideoListDataAdapter.SingleItemRowHolder(v);
        return singleItemRowHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int position) {
        SingleItemModel itemModel = itemModels.get(position);
        holder.tvTitle.setText(array_title_place[position % 8]);

          //holder.itemImage.setImageResource(array_image_place[1]);
        Typeface typeface = ResourcesCompat.getFont(mContext,R.font.iran_sans_mobile);
        holder.tvTitle.setTypeface(typeface, Typeface.BOLD);
        holder.itemImage.setBackgroundColor(Color.parseColor(array_image_place2[position % 4]));
       // Glide.with(mContext).load(getImage(array_image_place[position % 8])).into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return (null != itemModels ? itemModels.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle, tvCity, duration;
        protected ImageView itemImage;

        public SingleItemRowHolder(View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.title);
            this.itemImage = itemView.findViewById(R.id.image);
         //   this.tvCity = itemView.findViewById(R.id.tvCity);
            this.duration = itemView.findViewById(R.id.duration);
          //  Typeface typeface = itemView.getResources().getFont(R.font.iran_sans_mobile);
          //  tvPlace.setTypeface(typeface, Typeface.BOLD);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public int getImage(String imageName) {

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

        return drawableResourceId;
    }
}
