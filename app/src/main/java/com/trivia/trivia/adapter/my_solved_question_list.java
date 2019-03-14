package com.trivia.trivia.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class my_solved_question_list extends RecyclerView.Adapter<my_solved_question_list.MyViewHolder> {
    private String p_code;
    private List<Question> data_event_list;
    private SparseBooleanArray selected_items;
    Context context1;
    private int current_selected_idx = -1;
    OnCardClickListner onCardClickListner;
    private OnClickListener onClickListener = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_event_tv_main)
        TextView tv_main;
        @BindView(R.id.event_list_rcle)
        RecyclerView rv_event;
        @BindView(R.id.item_event_card)
        CardView cv_event;
@BindView(R.id.lyt_parent)
View lyt_parent;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public my_solved_question_list(ArrayList<Question> data_services_list) {
        this.data_event_list = data_services_list;
        selected_items = new SparseBooleanArray();

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_myquestions2, parent, false);
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
        uel.add(new list_item(data_event.getQ_score(),"effort","امتیاز",0));
        uel.add(new list_item(data_event.getQ_type(),"question","نوع سوال",Integer.parseInt(data_event.getQ_level())));
        adapter_list_event_unreg madapter = new adapter_list_event_unreg(uel);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        holder. rv_event.setAdapter(madapter);


      //  Glide.with(context1).load(data_event.getQ_img()).into(holder.iv_main);
      /*  holder.cv_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onCardClickListner.OnCardClicked(view, position);
            }
        });
        */

        holder.lyt_parent.setActivated(selected_items.get(position, false));

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.onItemClick(v, position);
            }
        });

        holder.lyt_parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onClickListener == null) return false;
                onClickListener.onItemLongClick(v,  position);
                return true;
            }
        });
        toggleCheckedIcon(holder, position);
    }
    private void toggleCheckedIcon(MyViewHolder holder, int position) {
        if (selected_items.get(position, false)) {
            //holder.lyt_image.setVisibility(View.GONE);
           // holder.lyt_checked.setVisibility(View.VISIBLE);
            if (current_selected_idx == position) resetCurrentIndex();
        } else {
           // holder.lyt_checked.setVisibility(View.GONE);
           // holder.lyt_image.setVisibility(View.VISIBLE);
            if (current_selected_idx == position) resetCurrentIndex();
        }
    }
    public void clearSelections() {
        selected_items.clear();
        notifyDataSetChanged();
    }
    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selected_items.size());
        for (int i = 0; i < selected_items.size(); i++) {
            items.add(selected_items.keyAt(i));
        }
        return items;
    }
    public int getSelectedItemCount() {
        return selected_items.size();
    }
    public Question getItem(int position) {
        return data_event_list.get(position);
    }
    public void toggleSelection(int pos) {
        current_selected_idx = pos;
        if (selected_items.get(pos, false)) {
            selected_items.delete(pos);
        } else {
            selected_items.put(pos, true);
        }
        notifyItemChanged(pos);
    }
    @Override
    public int getItemCount() {
        return data_event_list.size();
    }
    public void removeData(int position) {
        data_event_list.remove(position);
        resetCurrentIndex();
    }
    private void resetCurrentIndex() {
        current_selected_idx = -1;
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }
    public interface OnClickListener {
        void onItemClick(View view,  int pos);

        void onItemLongClick(View view, int pos);
    }

}