
package com.trivia.trivia.home.source;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapterRcycleSource;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.helper.Utils;
import com.trivia.trivia.util.obj_film;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_source_list extends myFragment {
    ProgressDialog mProgressDialog;
    FragmentActivity c;
    adapterRcycleSource madapter;
    @BindView(R.id.event_list_rcle)
    RecyclerView rcle;
    @BindView(R.id.bt_filter)
    ImageView iv_filter;
    FragmentSourcePresenter fragmentSourcePresenter;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_source, container, false);
        ButterKnife.bind(this, rootView);

        setRetainInstance(true);
        c = getActivity();
        // setToolbar(rootView, "سوالات حل نشده");
        mProgressDialog = new ProgressDialog(c);
        setFragmentActivity(getActivity());

        fragmentSourcePresenter = new FragmentSourcePresenter(this);
        // fragmentQuestionsPresenter.refreshEventList();
        bottom_sheet = rootView.findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();

            }
        });
        fragmentSourcePresenter.refreshEventList();
      /*  ArrayList<obj_film> obj_films = new ArrayList<>();
        obj_film o = new obj_film();
        o.setName("معرفی");
        o.setUrl("https://hw7.cdn.asset.aparat.com/aparat-video/24ea87883c80439a7dabc147247f939414081493-480p__46816.mp4");
        o.setType("video");
        obj_films.add(o);
        o = new obj_film();
        o.setName("معرفی 2");
        o.setUrl("http://dl.nex1music.ir/1397/12/19/Amir%20Abbasian%20-%20Hese%20Khoshbakhti%20[128].mp3?time=1552230952&filename=/1397/12/19/Amir%20Abbasian%20-%20Hese%20Khoshbakhti%20[128].mp3");
        o.setType("video");
        obj_films.add(o);

        o = new obj_film();
        o.setName("dummy.pdf");
        o.setUrl("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf");
        o.setType("pdf");
        obj_films.add(o);
        setItems(obj_films);
*/
        return rootView;

    }

    public void showBottomSheetDialog() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.filter_dialog, null);
        ((ImageView) view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });


        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);


        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

    public void setItems(ArrayList<obj_film> eventsList) {


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rcle.setLayoutManager(layoutManager);
        madapter = new adapterRcycleSource(eventsList);
        // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
        // alphaAdapter.setFirstOnly(true);
        rcle.setAdapter(madapter);
        madapter.setOnCardClickListner(new adapterRcycleSource.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                //  fragmentQuestionsPresenter.selectQuestion(eventsList.get(position));
                switch (eventsList.get(position).getType()) {
                    case "pdf":
                        File file = new File(Utils.getRootDirPath(getActivity()) + "/" + eventsList.get(position).getFname());
                        boolean fileExists = file.exists();
                        if (fileExists) {
                            if(Build.VERSION.SDK_INT>=24){
                                try{
                                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                                    m.invoke(null);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                            Uri path = Uri.fromFile(file);
                            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                            pdfIntent.setDataAndType(path, "application/pdf");
                            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            try{
                                startActivity(pdfIntent);
                            }catch(ActivityNotFoundException e){
                                Toast.makeText(getActivity(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //Toast.makeText(c, "دانلود", Toast.LENGTH_SHORT).show();
                            download(getActivity(),eventsList.get(position).getUrl(),eventsList.get(position).getFname());

                        }
                        break;
                    case "video":
                        Toast.makeText(c, "video", Toast.LENGTH_SHORT).show();
                     getActivity().startActivity( PlayerActivity.getStartingIntent(getActivity(),eventsList.get(position).getUrl()));
                      //  Intent i=new Intent(getContext(),PlayerActivity.class);
                      ////  getActivity().startActivity(i);
                        break;
                    case "audio":
                        Toast.makeText(c, "audio", Toast.LENGTH_SHORT).show();
                        getActivity().startActivity( PlayerActivity.getStartingIntent(getActivity(),eventsList.get(position).getUrl()));
                        //  Intent i=new Intent(getContext(),PlayerActivity.class);
                        ////  getActivity().startActivity(i);
                        break;
                }

            }
        });

    }

    public void showLoading() {

        showLoading_base();
    }

    public void hideLoading() {
        hideLoading_base();
    }
    public void download(Context context, String url, String name) {

        int downloadId = PRDownloader.download(url, Utils.getRootDirPath(context), name)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        Toast.makeText(context, "دانلود آغاز شد", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                       // tvprog.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));

                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(context, "دانلود تمام شد", Toast.LENGTH_SHORT).show();

                       // tvprog.setText("نمایش فایل راهنما");
                     //   fileExists=true;

                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(context, "اشکال در دانلود", Toast.LENGTH_SHORT).show();
                      //  tvprog.setText("دریافت فایل راهنما");
                    }
                });


    }


}