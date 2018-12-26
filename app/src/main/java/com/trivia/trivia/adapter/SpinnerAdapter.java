package com.trivia.trivia.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trivia.trivia.R;
import com.trivia.trivia.util.Languages;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<Languages> languages;

    public SpinnerAdapter(Context context, List<Languages> languages) {
        this.context = context;
        this.languages = languages;
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.spinner_layout, null);

        ImageView imageView = view.findViewById(R.id.spinnerImageView);
        TextView titleTextView = view.findViewById(R.id.title);
        titleTextView.setText(this.languages.get(i).getName());
        return view;
    }
}