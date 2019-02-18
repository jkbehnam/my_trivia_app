package com.trivia.trivia.base;

import android.app.ProgressDialog;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trivia.trivia.R;




public class myFragment extends Fragment {
    FragmentActivity a;
    ProgressDialog mProgressDialog;
    public void showLoading_base() {

        mProgressDialog.setTitle(null);
        mProgressDialog.setMessage(getResources().getString(R.string.activity_login_loading_msg));
        mProgressDialog.show();
    }

    public void hideLoading_base() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
    public void setFragmentActivity(FragmentActivity a){
        mProgressDialog =new ProgressDialog(a);
        this.a=a;
    }
    public void setToolbar(View rootView,String title){
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        tvToolbarTitle.setText(title);
    }
}
