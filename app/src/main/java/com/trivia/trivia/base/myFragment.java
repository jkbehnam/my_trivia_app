package com.trivia.trivia.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trivia.trivia.R;


public class myFragment extends Fragment {
    FragmentActivity a;
    ProgressDialog mProgressDialog;
    public void showLoading() {

        mProgressDialog.setTitle(null);
        mProgressDialog.setMessage(getResources().getString(R.string.activity_login_loading_msg));
        mProgressDialog.show();
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
    public void setFragmentActivity(FragmentActivity a){
        mProgressDialog =new ProgressDialog(a);
        this.a=a;
    }
}
