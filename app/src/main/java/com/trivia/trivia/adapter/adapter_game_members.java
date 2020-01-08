package com.trivia.trivia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.trivia.trivia.R;
import com.trivia.trivia.activities.Game.Fragment_main_event;
import com.trivia.trivia.util.Member;
import com.trivia.trivia.util.OtherGamer;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapter_game_members extends RecyclerView.Adapter<adapter_game_members.MyViewHolder> {
    private List<Member> data_services_list;
    Context context1;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView q_name, tv_correct, tv_wrong, q_get_time;
        LinearLayout li;


        public MyViewHolder(View view) {
            super(view);
            q_name = view.findViewById(R.id.tv_name);
            tv_correct = view.findViewById(R.id.tv_correct);
            tv_wrong = view.findViewById(R.id.tv_wrong);


            li = view.findViewById(R.id.relativeLayout1);
        }
    }


    public adapter_game_members(ArrayList<Member> data_services_list, String p_code) {
        this.data_services_list = data_services_list;
        String p_code1 = p_code;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_member2, parent, false);
        context1 = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Member data_service = data_services_list.get(position);


        holder.q_name.setText(data_service.getU_name());
        holder.tv_correct.setText(String.valueOf(data_service.getU_correct_answer()));
        holder.tv_wrong.setText(data_service.getU_wrong_answer());



    }
    @Override
    public int getItemViewType(int position) {
        return position;
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