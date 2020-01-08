package com.trivia.trivia.activities.Game.GameAnswerQuestion.MultipleChoiceAlert;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Game.GameAnswerQuestion.BaseAnswer;
import com.trivia.trivia.activities.Game.GameGamerQuestion.FragmentQuestionsPresenter;
import com.trivia.trivia.adapter.adapter_game_multiple_choice;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.MultipleChoicePage;
import com.trivia.trivia.util.Question;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class MultipleChoiceAlert extends BaseAnswer implements IGamesMC {
    Question question;
    Context context;
    Dialog dialog;

    @BindView(R.id.fragment_multi_choice_iv_question)
    ImageView iv_question;
    @BindView(R.id.rcyc_multichoise)
    RecyclerView rcye_qustions;
    @BindView(R.id.fragment_multi_choice_tv_question)
    TextView tvQTitle;
    @BindView(R.id.tv_level)
    TextView tv_level;
    @BindView(R.id.tv_wrong)
    TextView tv_wrong;
    @BindView(R.id.fragment_multi_choice_tv_score)
    TextView tvScore;
    @BindView(R.id.fragment_multi_choice_tv_2)
    TextView tv_im_question;
    @BindView(R.id.back_arrow)
    ImageView back_arrow;
    adapter_game_multiple_choice madapter;

    MultipleChoicePage mpcp;
    MCPresenter answerQuestionPresenter;
    FragmentQuestionsPresenter fragmentQuestionsPresenter = null;
    int position;

    @SuppressLint("ResourceAsColor")
    public Dialog init_dialog(Context context, Question question, FragmentQuestionsPresenter fragmentQuestionsPresenter, int position) {
        this.context = context;
        this.question = question;
        this.position = position;

        dialog = new Dialog(context, android.R.style.Theme_Holo_Light_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_multiple_choice);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, dialog);

        //==============
        initLodingDialog(context);
        this.fragmentQuestionsPresenter = fragmentQuestionsPresenter;
        answerQuestionPresenter = new MCPresenter(this, question);
        answerQuestionPresenter.getMultipleChoice();
        answerQuestionPresenter.getQuestionScore(question.getId());
        setscore("");
        tv_level.setText("");
        tv_wrong.setText("");
        //==========
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            dialog.getWindow().setStatusBarColor(dialog.getContext().getResources().getColor(R.color.game_status));
            dialog.getWindow().setNavigationBarColor(dialog.getContext().getResources().getColor(R.color.game_nav));
        }

        Toast.makeText(context, "با هر جواب اشتباه 20 درصد از امتیاز سوال کم میشود.", Toast.LENGTH_SHORT).show();
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public void show() {
        dialog.show();
    }


    @Override
    public void correcAnswer(int item) {
        mpcp.getMpi().get(item).setState(1);
        madapter.notifyItemChanged(item);
        Toasty.success(context, "جواب صحیح است.", Toast.LENGTH_SHORT, true).show();
        fragmentQuestionsPresenter.removeItem(position);
    }

    @Override
    public void wrongAnswer(int item) {
        mpcp.getMpi().get(item).setState(2);
        madapter.notifyItemChanged(item);
        Toasty.error(context, "جواب نادرست است.", Toast.LENGTH_SHORT, true).show();
        question.setQ_try_num(question.getQ_try_num() + 1);
        tv_wrong.setText(String.valueOf(question.getQ_try_num()));
        if (question.getQ_try_num() == 2) {
            fragmentQuestionsPresenter.removeItem(position);
            finishFrag();
        }
    }


    @Override
    public void setItems(MultipleChoicePage mpcp2) {
        tv_wrong.setText(String.valueOf(question.getQ_try_num()));
        tv_level.setText(answerQuestionPresenter.returnLevel(question.getQ_level()));
        mpcp = mpcp2;

        if (question.getQ_img().isEmpty() || question.getQ_img() == null) {
            tvQTitle.setVisibility(View.VISIBLE);
            tvQTitle.setText(question.getQ_title());
            iv_question.setVisibility(View.GONE);
            tv_im_question.setVisibility(View.GONE);
        } else {
            tvQTitle.setVisibility(View.GONE);
            tv_im_question.setVisibility(View.VISIBLE);
            iv_question.setVisibility(View.VISIBLE);
            Glide.with(context).load(question.getQ_img()).into(iv_question);
            tv_im_question.setText(question.getQ_title());
        }

        GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
        rcye_qustions.setLayoutManager(layoutManager);
        madapter = new adapter_game_multiple_choice(mpcp.getMpi());
        rcye_qustions.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapter_game_multiple_choice.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                PrefManager pm = new PrefManager(context);

                answerQuestionPresenter.checkAnswer(mpcp.getMpi().get(position).getTitle(), question.getQ_id(), question.getId(), position, pm.getUserDetails().get("u_id"));
            }
        });
    }

    @Override
    public void showLoading() {
        showLoadingBase();
    }

    @Override
    public void hideLoading() {
        hindLoadingBase();
    }


    @Override
    public void setscore(String score) {
        tvScore.setText("" + score);
    }

    @Override
    public void finishFrag() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 800);
    }



}
