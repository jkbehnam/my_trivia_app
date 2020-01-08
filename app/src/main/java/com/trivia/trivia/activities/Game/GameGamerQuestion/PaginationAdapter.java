package com.trivia.trivia.activities.Game.GameGamerQuestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trivia.trivia.R;
import com.trivia.trivia.activities.Events.DialogEventEnd;
import com.trivia.trivia.activities.Profile.VolleyCallback;
import com.trivia.trivia.adapter.adapter_event_items;
import com.trivia.trivia.custom_widgets.PaginationAdapterCallback;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.util.URLs;
import com.trivia.trivia.util.list_item;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suleiman on 19/10/16.
 */

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HERO = 2;
    Context context1;
    OnCardClickListner onCardClickListner;
    OnSellClickListner onSellClickListner;


    private List<Question> movieResults;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;

    PaginationAdapter(Context context) {
        this.context = context;
        this.mCallback = (PaginationAdapterCallback) context;
        movieResults = new ArrayList<>();
    }

    public void removeitem(int position) {
        try {
            movieResults.remove(position);

            notifyItemChanged(position);
            notifyItemRangeChanged(position, movieResults.size());
        } catch (Exception e) {
        }

    }

    public List<Question> getMovies() {
        return movieResults;
    }

    public void setMovies(List<Question> movieResults) {
        this.movieResults = movieResults;
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position, Question q);
    }

    public void setOnCardClickListner(PaginationAdapter.OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

    public interface OnSellClickListner {
        void OnSellClicked(View view, int position, Question q);
    }

    public void setOnSellClickListner(PaginationAdapter.OnSellClickListner onSellClickListner) {
        this.onSellClickListner = onSellClickListner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        context1 = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_myquestions, parent, false);
                viewHolder = new QuestionVH(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Question data_event = movieResults.get(position); // Movie

        switch (getItemViewType(position)) {

            case ITEM:
                final QuestionVH questionVH = (QuestionVH) holder;

                questionVH.tv_main.setText(data_event.getQ_title());

                switch (data_event.getQ_state()) {
                    case 0:
                        questionVH.iv_obs.setVisibility(View.INVISIBLE);
                        questionVH.cv_event.setClickable(true);
                        questionVH.cv_event.setEnabled(true);
                        break;
                    case 5:
                        questionVH.iv_obs.setVisibility(View.VISIBLE);
                        questionVH.iv_obs.setText("برای داور ارسال شده");
                        questionVH.cv_event.setClickable(false);
                        questionVH.cv_event.setEnabled(false);
                        break;
                    case 6:
                        questionVH.iv_obs.setVisibility(View.VISIBLE);
                        questionVH.iv_obs.setText("پاسخ مردود شد");
                        questionVH.cv_event.setClickable(true);
                        questionVH.cv_event.setEnabled(true);
                        break;
                }
                //    questionVH.iv_obs.setText(data_event.getQ_title());
                GridLayoutManager layoutManager = new GridLayoutManager(context1, 2);
                questionVH.rv_event.setLayoutManager(layoutManager);
                ArrayList<list_item> uel = new ArrayList<list_item>();
                //  uel.add(new list_item(data_event.getQ_score(), "dollar", "امتیاز", 0));
                uel.add(new list_item(data_event.getQ_score(), "dollar", "", 0));
                switch (Integer.parseInt(data_event.getQ_level())) {
                    case 1:
                        questionVH.view_.setBackgroundColor(context.getResources().getColor(R.color.q_color_green));
                        // holder.img.setColorFilter(ContextCompat.getColor(context, R.color.q_color_green), android.graphics.PorterDuff.Mode.SRC_IN);

                        break;
                    case 2:
                        questionVH.view_.setBackgroundColor(context.getResources().getColor(R.color.q_color_yellow));
                        //  holder.img.setColorFilter(ContextCompat.getColor(context, R.color.q_color_yellow), android.graphics.PorterDuff.Mode.SRC_IN);

                        break;
                    case 3:
                        questionVH.view_.setBackgroundColor(context.getResources().getColor(R.color.q_color_red));
                        // holder.img.setColorFilter(ContextCompat.getColor(context, R.color.q_color_red), android.graphics.PorterDuff.Mode.SRC_IN);

                        break;
                }
                uel.add(new list_item(gettype(data_event.getQ_type()), "effort2", "", Integer.parseInt(data_event.getQ_level())));
                adapter_event_items madapter = new adapter_event_items(uel);
                // SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(madapter);
                // alphaAdapter.setFirstOnly(true);
                questionVH.rv_event.setAdapter(madapter);


                //  Glide.with(context1).load(data_event.getQ_img()).into(holder.iv_main);
                questionVH.cv_event.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        onCardClickListner.OnCardClicked(view, position, data_event);
                    }
                });


                questionVH.lay_sell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //creating a popup menu
                        int menu;
                        if (data_event.getQ_type().equals("DescAnswer")) {
                            menu = R.menu.menu_sell_desc;
                        } else {
                            menu = R.menu.menu_sell;
                        }
                        PopupMenu popup = new PopupMenu(context, questionVH.iv_sell);
                        //inflating menu from xml resource
                        popup.inflate(menu);
                        //adding click listener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.sell:
                                        onSellClickListner.OnSellClicked(view, position, data_event);
                                        return true;

                                    case R.id.obs_answer:
                                        getEventCapacity(data_event.getId());
                                        return true;
                                    default:
                                        return true;
                                }
                            }
                        });
                        //displaying the popup
                        popup.show();


                    }
                });

                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return movieResults == null ? 0 : movieResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM;
        } else {
            return (position == movieResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
        }
    }

/*
    private GlideRequest<Drawable> loadImage(@NonNull String posterPath) {
        return GlideApp
                .with(context)
                .load(BASE_URL_IMG + posterPath)
                .centerCrop();
    }

*/
    /*
        Helpers - Pagination
   _________________________________________________________________________________________________
    */

    public void add(Question r) {
        movieResults.add(r);
        notifyItemInserted(movieResults.size() - 1);
    }

    public void addAll(List<Question> moveResults) {
        for (Question result : moveResults) {
            add(result);
        }
    }

    public void remove(Question r) {
        int position = movieResults.indexOf(r);
        if (position > -1) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Question());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieResults.size() - 1;
        if (position >= 0) {
            Question result = getItem(position);

            if (result != null) {
                movieResults.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public Question getItem(int position) {
        return movieResults.get(position);
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(movieResults.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Header ViewHolder
     */


    /**
     * Main list's content ViewHolder
     */
    protected class QuestionVH extends RecyclerView.ViewHolder {
        @BindView(R.id.item_event_tv_main)
        TextView tv_main;
        @BindView(R.id.event_list_rcle)
        RecyclerView rv_event;
        @BindView(R.id.item_event_card)
        CardView cv_event;
        @BindView(R.id.view_)
        View view_;
        @BindView(R.id.lay_sell)
        LinearLayout lay_sell;
        @BindView(R.id.iv_sell)
        ImageView iv_sell;
        @BindView(R.id.iv_obs)
        TextView iv_obs;


        public QuestionVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

    void getEventCapacity(String q_id) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("q_id", q_id);

        connectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                JSONObject obj = new JSONObject(result);
                String message = obj.getString("answer");
                try {
                    AlertDialog ad;
                    DialogEventEnd dt = new DialogEventEnd();
                    ad = dt.init(context, message, "");
                    Button btn_ok_alert = dt.getview().findViewById(R.id.event_detail_btn);
                    btn_ok_alert.setVisibility(View.GONE);

                    ad.show();
                } catch (Exception e) {
                }
                ;

            }
        }, param, URLs.URL_GET_OBS_OPINION);

    }

    public String gettype(String s) {
        switch (s) {
            case "ShortAnswer":
                return "جواب کوتاه";
            case "MultipleChoice":
                return "تستی";
            case "DescAnswer":
                return "تشریحی";
            default:
                return s;
        }

    }
}
