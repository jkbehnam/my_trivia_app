package com.trivia.trivia.activities.Game.GameSellQuestion.SellQuestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trivia.trivia.R;
import com.trivia.trivia.adapter.adapter_event_items;
import com.trivia.trivia.custom_widgets.PaginationAdapterCallback;
import com.trivia.trivia.util.SellQuestion;
import com.trivia.trivia.util.list_item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suleiman on 19/10/16.
 */

public class sellPaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HERO = 2;
    Context context1;
    OnCardClickListner onCardClickListner;


    private List<SellQuestion> questionResults;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;

    sellPaginationAdapter(Context context) {
        this.context = context;
        this.mCallback = (PaginationAdapterCallback) context;
        questionResults = new ArrayList<>();
    }

    public void removeitem(int position) {
        questionResults.remove(position);

        notifyItemChanged(position);
        notifyItemRangeChanged(position, questionResults.size());
    }

    public List<SellQuestion> getMovies() {
        return questionResults;
    }

    public void setMovies(List<SellQuestion> movieResults) {
        this.questionResults = movieResults;
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position, SellQuestion q);
    }

    public void setOnCardClickListner(sellPaginationAdapter.OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        context1 = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_sellquestions, parent, false);
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
        SellQuestion data_event = questionResults.get(position); // Movie

        switch (getItemViewType(position)) {

            case ITEM:
                final QuestionVH questionVH = (QuestionVH) holder;

                questionVH.tv_main.setText(data_event.getQ_title());

                GridLayoutManager layoutManager = new GridLayoutManager(context1, 2);
                questionVH.rv_event.setLayoutManager(layoutManager);
                ArrayList<list_item> uel = new ArrayList<list_item>();
                //  uel.add(new list_item(data_event.getQ_score(), "dollar", "امتیاز", 0));
                uel.add(new list_item(data_event.getQ_score(), "dollar", "", 0));
                uel.add(new list_item(data_event.getPrice()+"قیمت", "dollar", "", 0));
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
                uel.add(new list_item(data_event.getQ_type(), "effort2", "", Integer.parseInt(data_event.getQ_level())));
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
                        PopupMenu popup = new PopupMenu(context, questionVH.iv_sell);
                        //inflating menu from xml resource
                        popup.inflate(R.menu.menu_sell);
                        //adding click listener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.sell:
                                        //handle menu1 click
                                        return true;
                                    default:
                                        return false;
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
        return questionResults == null ? 0 : questionResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM;
        } else {
            return (position == questionResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
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

    public void add(SellQuestion r) {
        questionResults.add(r);
        notifyItemInserted(questionResults.size() - 1);
    }

    public void addAll(List<SellQuestion> moveResults) {
        for (SellQuestion result : moveResults) {
            add(result);
        }
    }

    public void remove(SellQuestion r) {
        int position = questionResults.indexOf(r);
        if (position > -1) {
            questionResults.remove(position);
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
        add(new SellQuestion());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = questionResults.size() - 1;
        if (position >= 0) {
            SellQuestion result = getItem(position);

            if (result != null) {
                questionResults.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public SellQuestion getItem(int position) {
        return questionResults.get(position);
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(questionResults.size() - 1);

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

}
