package com.trivia.trivia.activities.Events.multitype;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.trivia.trivia.R;


import com.trivia.trivia.activities.Messages.GlideApp;
import com.trivia.trivia.util.Event;

import java.util.ArrayList;
import java.util.HashMap;



/**
 * Created by anupamchugh on 09/02/16.
 */
public class MultiViewTypeAdapter extends RecyclerView.Adapter {

    private ArrayList<SectionDataModel> dataList;
    Context mContext;
    int total_types;
    OnCardClickListner onCardClickListner;
    private SnapHelper snapHelper;
    private SnapHelper snapHelper2;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public class TextTypeViewHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView recyclerView;
        protected TextView btnMore;
        // protected ImageView ivicon;

        public TextTypeViewHolder(View itemView) {
            super(itemView);
            this.itemTitle = itemView.findViewById(R.id.itemTitle);
            this.recyclerView = itemView.findViewById(R.id.recycler_view_list);
            this.btnMore = itemView.findViewById(R.id.btnMore);
            // this.ivicon = itemView.findViewById(R.id.ivicon);
        }
    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;
        protected RecyclerView recyclerView;
        protected TextView btnMore;
        //  protected ImageView ivicon;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.itemTitle = itemView.findViewById(R.id.itemTitle);
            this.recyclerView = itemView.findViewById(R.id.recycler_view_list);
            this.btnMore = itemView.findViewById(R.id.btnMore);
            // this.ivicon = itemView.findViewById(R.id.ivicon);
        }
    }

    public static class AudioTypeViewHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected TextView duration;
        protected TextView video_title;

        PlayerView mPlayerView;
        // FloatingActionButton fab;
        TextView buttonmore;

        ImageView ivplay;
        private SimpleExoPlayer mPlayer;
        private String mVideoUrl;
        RelativeLayout lay_forground;
        private long mCurrentMillis;
        MaterialRippleLayout materialRippleLayoutl;

        public AudioTypeViewHolder(View itemView) {
            super(itemView);
            this.itemTitle = itemView.findViewById(R.id.itemTitle);
            this.mPlayerView = itemView.findViewById(R.id.videoicon);
            this.ivplay = itemView.findViewById(R.id.image);
            lay_forground = itemView.findViewById(R.id.lay_forground);
            this.buttonmore = itemView.findViewById(R.id.btnMore);
            this.duration = itemView.findViewById(R.id.duration);
            this.video_title = itemView.findViewById(R.id.video_title);
            // this.fab = (FloatingActionButton) itemView.findViewById(R.id.fab);
        }

    }

    public static class BannerTypeViewHolder extends RecyclerView.ViewHolder {


        public BannerTypeViewHolder(View itemView) {
            super(itemView);

            // this.fab = (FloatingActionButton) itemView.findViewById(R.id.fab);
        }

    }

    public MultiViewTypeAdapter(ArrayList<SectionDataModel> dataList, Context context) {
        this.dataList = dataList;
        this.mContext = context;
        total_types = dataList.size();
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                snapHelper = new GravitySnapHelper(Gravity.START);
                return new TextTypeViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_type, parent, false);
                snapHelper2 = new GravitySnapHelper(Gravity.START);
                return new ImageTypeViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item23, parent, false);
                return new AudioTypeViewHolder(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_banner, parent, false);
                return new BannerTypeViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (dataList.get(position).getType()) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        SectionDataModel object = dataList.get(listPosition);
        if (object != null) {
            switch (object.getType()) {
                case 0: {
                    String sectionName = dataList.get(listPosition).getHeaderTitle();
                    ArrayList singleSectionItems = dataList.get(listPosition).getAllEventInSection();
                    ((TextTypeViewHolder) holder).itemTitle.setText(sectionName);
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.iran_sans_mobile);
                    ((TextTypeViewHolder) holder).itemTitle.setTypeface(typeface, Typeface.BOLD);
                    SectionListEventAdapter adapter = new SectionListEventAdapter(singleSectionItems, mContext);
                    ((TextTypeViewHolder) holder).recyclerView.setHasFixedSize(false);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true);
                    layoutManager.setReverseLayout(true);
                    ((TextTypeViewHolder) holder).recyclerView.setLayoutManager(layoutManager);
                    ((TextTypeViewHolder) holder).recyclerView.setNestedScrollingEnabled(false);
                    ((TextTypeViewHolder) holder).recyclerView.setRecycledViewPool(recycledViewPool);
                    ((TextTypeViewHolder) holder).recyclerView.setAdapter(adapter);
                    adapter.setOnCardClickListner(new SectionListEventAdapter.OnCardClickListner() {
                        @Override
                        public void OnCardClicked(View view, Event position) {
                            onCardClickListner.OnCardClicked(view, position);
                        }
                    });
                    ((TextTypeViewHolder) holder).btnMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onCardClickListner.OnCardViewMoreClicked(view);
                        }
                    });
                 /*  ((TextTypeViewHolder) holder).ivicon.setAlpha(1f);

                    ((TextTypeViewHolder) holder).recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            ((TextTypeViewHolder) holder).recyclerView.computeHorizontalScrollRange();
                            ((TextTypeViewHolder) holder).ivicon.setAlpha(((float) (((TextTypeViewHolder) holder).recyclerView.computeHorizontalScrollOffset() - 7380) / ((float) (7800 - 7380))));


                        }
                    });

                    */
                    // holder.recyclerView.addItemDecoration(new PaddingItemDecoration(500));
                    snapHelper.attachToRecyclerView(((TextTypeViewHolder) holder).recyclerView);

                }
                break;
                case 1: {
                    String sectionName2 = dataList.get(listPosition).getHeaderTitle();
                    ArrayList singleSectionItems2 = dataList.get(listPosition).getSingleItemModels();
                    ((ImageTypeViewHolder) holder).itemTitle.setText("ویدویو های اخیر");
                    Typeface typeface2 = ResourcesCompat.getFont(mContext, R.font.iran_sans_mobile);
                    ((ImageTypeViewHolder) holder).itemTitle.setTypeface(typeface2, Typeface.BOLD);
                    SectionVideoListDataAdapter adapter2 = new SectionVideoListDataAdapter(singleSectionItems2, mContext);
                    ((ImageTypeViewHolder) holder).recyclerView.setHasFixedSize(false);

                    LinearLayoutManager layoutManager2 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true);
                    //layoutManager2.setReverseLayout(true);
                    ((ImageTypeViewHolder) holder).recyclerView.setLayoutManager(layoutManager2);

                    ((ImageTypeViewHolder) holder).recyclerView.setNestedScrollingEnabled(false);
                    //  ((ImageTypeViewHolder) holder).recyclerView.setRecycledViewPool(recycledViewPool);
                    ((ImageTypeViewHolder) holder).recyclerView.setAdapter(adapter2);
                    snapHelper2.attachToRecyclerView(((ImageTypeViewHolder) holder).recyclerView);
                    ((ImageTypeViewHolder) holder).btnMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(view.getContext(), "Button More Clicked!" + sectionName2, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                break;
                case 2:
                 //   Glide.with(mContext).load(getImage("back_blue")).into(((AudioTypeViewHolder) holder).ivplay);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {

                           GlideApp.with(mContext)
                                    .asBitmap()
                                    .load("https://static.cdn.asset.aparat.com/avt/3899524-1889-b__255178407.jpg")
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(((AudioTypeViewHolder) holder).ivplay);

                            //  ((AudioTypeViewHolder) holder). myVideoView.setVideoURI(Uri.parse("https://s-v2.tamasha.com/statics/videos_download/d1/13/k3eXM_d113e5adf9962f22e2521ce30fc8667cb58fcde2_n_144.mp4?name=%D8%B9%D9%85%D9%84%DA%A9%D8%B1%D8%AF_%D8%B4%D8%A7%DA%AF%D8%B1%D8%AF%D8%A7%D9%86_%D8%AC%D8%A8%D8%A7%D8%B1%DB%8C_%D8%AF%D8%B1_%D8%AD%D8%B1%DA%A9%D8%A7%D8%AA_%D9%BE%D8%A7_%D8%A8%D9%87_%D8%AA%D9%88%D9%BE_auto.mp4"));
                            //  ((AudioTypeViewHolder) holder). myVideoView.setMediaController(new MediaController(mContext));
                            //  ((AudioTypeViewHolder) holder). myVideoView.requestFocus();


                            ((AudioTypeViewHolder) holder).itemTitle.setText("فیلم ها");
                            ((AudioTypeViewHolder) holder).video_title.setText("سخنرانی دکتر مجید میرزاوزیری در اولین دوره از Ferdowsi Talks");
                            Typeface typeface2 = ResourcesCompat.getFont(mContext, R.font.iran_sans_mobile);
                            ((AudioTypeViewHolder) holder).itemTitle.setTypeface(typeface2, Typeface.BOLD);


                       //     MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                         //   retriever.setDataSource("https://hw13.cdn.asset.aparat.com/aparat-video/34b2535214ce67d4009884c40bced4cd15125844-144p__61576.mp4");
                         //   String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                         //   retriever.release();



                     //       long timeInMillisec = Long.parseLong(time);
                      //      String duration=convertMillieToHMmSs(timeInMillisec);
                      //      ((AudioTypeViewHolder) holder).duration.setText(duration);
                            ((AudioTypeViewHolder) holder).duration.setText("");
                            ((AudioTypeViewHolder) holder).lay_forground.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (((AudioTypeViewHolder) holder).mPlayer != null) {
                                        // no need to continue creating the player, if it's probably there
                                        return;
                                    }
                                    // set default options for the player
                                    ((AudioTypeViewHolder) holder).mPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(mContext),
                                            new DefaultTrackSelector());
                                    ((AudioTypeViewHolder) holder).mPlayerView.setPlayer(((AudioTypeViewHolder) holder).mPlayer);

                                    DefaultDataSourceFactory dataSourceFactory =
                                            new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, "player"));
                                    ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse("https://hw13.cdn.asset.aparat.com/aparat-video/34b2535214ce67d4009884c40bced4cd15125844-144p__61576.mp4"));

                                    // now is the more important part. here we check to see if we want to resume, or start from the beggining
                                    boolean isResuming = ((AudioTypeViewHolder) holder).mCurrentMillis != 0;
                                    ((AudioTypeViewHolder) holder).mPlayer.prepare(extractorMediaSource, isResuming, false);
                                    ((AudioTypeViewHolder) holder).mPlayer.setPlayWhenReady(true);

                                    ((AudioTypeViewHolder) holder).mPlayer.addListener(new Player.EventListener() {
                                        @Override
                                        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
                                            ((AudioTypeViewHolder) holder).mPlayer.setPlayWhenReady(true);
                                            ((AudioTypeViewHolder) holder).mPlayer.getPlaybackState();
                                        }

                                        @Override
                                        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                                        }

                                        @Override
                                        public void onLoadingChanged(boolean isLoading) {

                                        }

                                        @Override
                                        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                                            switch (playbackState) {
                                                case SimpleExoPlayer.STATE_BUFFERING:
                                                    break;
                                                case SimpleExoPlayer.STATE_ENDED:
                                                    //do what you want
                                                    break;
                                                case SimpleExoPlayer.STATE_IDLE:
                                                    break;
                                                case SimpleExoPlayer.STATE_READY:
                                                    ((AudioTypeViewHolder) holder).lay_forground.setVisibility(View.GONE);
                                                    break;
                                                default:
                                                    break;
                                            }

                                        }

                                        @Override
                                        public void onRepeatModeChanged(int repeatMode) {

                                        }

                                        @Override
                                        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                                        }

                                        @Override
                                        public void onPlayerError(ExoPlaybackException error) {

                                        }

                                        @Override
                                        public void onPositionDiscontinuity(int reason) {

                                        }

                                        @Override
                                        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                                        }

                                        @Override
                                        public void onSeekProcessed() {

                                        }
                                    });
                                    if (isResuming) {
                                        // want to resume? seek to the old position
                                        ((AudioTypeViewHolder) holder).mPlayer.seekTo(((AudioTypeViewHolder) holder).mCurrentMillis);
                                    }
                                }
                            });



                        }
                    });






                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();

        super.onViewRecycled(holder);
    }
    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {

        super.onViewDetachedFromWindow(holder);

        try {
            ((AudioTypeViewHolder) holder).mPlayer.stop();
        }catch (Exception e){}


    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {

        int childCount = recyclerView.getChildCount();

        for (int i = 0; i < childCount; i++) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            if (holder != null &&(holder instanceof AudioTypeViewHolder)) {
                ((AudioTypeViewHolder) holder).mPlayer.setPlayWhenReady(false);
                ((AudioTypeViewHolder) holder).mPlayer.getPlaybackState();

            }
        }


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public int getImage(String imageName) {

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

        return drawableResourceId;
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, Event position);
        void OnCardViewMoreClicked(View view);

    }
public void releaseExo(){}
    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }
    public static String convertMillieToHMmSs(long millie) {
        long seconds = (millie / 1000);
        long second = seconds % 60;
        long minute = (seconds / 60) % 60;
        long hour = (seconds / (60 * 60)) % 24;

        String result = "";
        if (hour > 0) {
            return String.format("%02d:%02d:%02d", hour, minute, second);
        }
        else {
            return String.format("%02d:%02d" , minute, second);
        }

    }
    public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

}
