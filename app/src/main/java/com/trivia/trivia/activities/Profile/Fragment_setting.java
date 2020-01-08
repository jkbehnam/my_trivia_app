
package com.trivia.trivia.activities.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.trivia.trivia.R;
import com.trivia.trivia.activities.HomeBase.Home;
import com.trivia.trivia.util.App;
import com.trivia.trivia.util.Event;
import com.trivia.trivia.util.Languages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_setting extends BottomSheetDialogFragment implements View.OnClickListener {


    @BindView(R.id.acount_btn)
    LinearLayout acount_btn;
    @BindView(R.id.lang_btn)
    LinearLayout lang_btn;

    List<Languages> langList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_setting_sectioned, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        //setToolbar(rootView, "صفحه شخصی");

        acount_btn.setOnClickListener(this);
        lang_btn.setOnClickListener(this);

       
        
        
        
        return rootView;
    }


    public static Fragment_setting newInstance(Event event) {
        Fragment_setting fragment = new Fragment_setting();
        Bundle bundle = new Bundle();
        //  bundle.putSerializable(EVENT_KEY, event);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acount_btn:
                Intent i = new Intent(getActivity(), reg_data.class);
                startActivity(i);
                break;
            case R.id.lang_btn:
                showGenderDialog();
                break;
        }
    }
    private void showGenderDialog() {
int select = 0;
        langList = Arrays.asList(new Languages("فارسی", "fa", 11), new Languages("English", "en", 11));
        String lng = App.localeManager.getLanguage();
        for (Languages l : langList
        ) {
            if (l.getCode().equals(lng)) {
                select =langList.indexOf(l);
                break;
            }
        }
        
        
        ArrayList<String> a = new ArrayList<>();
        final CharSequence[] charSequenceArr = new String[]{"فارسی", "انگلیسی"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("زبان");
        builder.setSingleChoiceItems(charSequenceArr, select, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "زبان های دریگر برنامه به زودی ارائه می شوند.", Toast.LENGTH_SHORT).show();

              //  setNewLocale(langList.get(i).getCode(), true);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    private boolean setNewLocale(String language, boolean restartProcess) {
        App.localeManager.setNewLocale(getActivity().getApplicationContext(), language);
        Intent i = new Intent(getActivity(), Home.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

        if (restartProcess) {
            System.exit(0);
        } else {
            Toast.makeText(getActivity(), "Activity restarted", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}