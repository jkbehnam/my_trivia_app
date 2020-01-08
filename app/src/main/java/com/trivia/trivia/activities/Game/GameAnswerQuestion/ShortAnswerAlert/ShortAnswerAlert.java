package com.trivia.trivia.activities.Game.GameAnswerQuestion.ShortAnswerAlert;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.codesgood.views.JustifiedTextView;
import com.trivia.trivia.R;
import com.trivia.trivia.activities.Game.GameAnswerQuestion.BaseAnswer;
import com.trivia.trivia.activities.Game.GameBank.PinEntryEditText;
import com.trivia.trivia.activities.Game.GameGamerQuestion.FragmentQuestionsPresenter;
import com.trivia.trivia.helper.PrefManager;
import com.trivia.trivia.util.Question;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class ShortAnswerAlert  extends BaseAnswer implements IGamesSA {
    Question question;
    Context context;
    Dialog dialog;
    PinEntryEditText et_answer = null;
    @BindView(R.id.fragment_multi_choice_iv_question)
    ImageView iv_question;
    @BindView(R.id.animation)
    View animation;
    @BindView(R.id.fragment_multi_choice_tv_question)
    JustifiedTextView tvQTitle;
    @BindView(R.id.fragment_multi_choice_tv_score)
    TextView tvScore;
    @BindView(R.id.fragment_multi_choice_tv)
    TextView tv_im_question;
    @BindView(R.id.et_short_answer)
    EditText et_short_answer;
    @BindView(R.id.btn_ok)
    TextView btn_ok;
    @BindView(R.id.tv_level)
    TextView tv_level;

    @BindView(R.id.tv_wrong)
    TextView tv_wrong;
    @BindView(R.id.back_arrow)
    ImageView back_arrow;
    //@BindView(R.id.fragment_multi_choice_cv_question)
    // CardView cv_answer;
    // @BindView(R.id.scrollv)
    // ScrollView scrollv;
    SAPresenter answerQuestionPresenter;
    FragmentQuestionsPresenter fragmentQuestionsPresenter = null;
    int position;

    @SuppressLint("ResourceAsColor")
    public Dialog init_dialog(Context context, Question question, FragmentQuestionsPresenter fragmentQuestionsPresenter, int position) {
        this.context = context;
        this.question = question;
        this.fragmentQuestionsPresenter = fragmentQuestionsPresenter;
        this.position = position;
        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        context2=context;
        initLodingDialog();
        answerQuestionPresenter = new SAPresenter(this, question);
        dialog = new Dialog(context, android.R.style.Theme_Holo_Light_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_short_answer);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, dialog);
        animation.setVisibility(View.GONE);
        tvQTitle.setVisibility(View.VISIBLE);
        tvQTitle.setMovementMethod(new ScrollingMovementMethod());
        et_answer = dialog.findViewById(R.id.txt_pin_entry);


        answerQuestionPresenter.getAnswerLenght();
        answerQuestionPresenter.getQuestionScore(question.getId());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            dialog.getWindow().setStatusBarColor(dialog.getContext().getResources().getColor(R.color.game_status));
            dialog.getWindow().setNavigationBarColor(dialog.getContext().getResources().getColor(R.color.game_nav));
        }
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

    public void initItem(int st) {
        tv_wrong.setText(String.valueOf(question.getQ_try_num()));
        tv_level.setText(answerQuestionPresenter.returnLevel(question.getQ_level()));
        if (question.getQ_img().isEmpty() || question.getQ_img() == null) {
            tvQTitle.setVisibility(View.VISIBLE);
            tvQTitle.setText(question.getQ_title());
            iv_question.setVisibility(View.GONE);
            tv_im_question.setVisibility(View.GONE);

        } else {

            tvQTitle.setVisibility(View.INVISIBLE);
            tv_im_question.setVisibility(View.VISIBLE);
            iv_question.setVisibility(View.VISIBLE);
            Glide.with(context).load(question.getQ_img()).into(iv_question);
            tv_im_question.setText(question.getQ_title());
        }

        if (question.getQ_string().equals("1")) {
            btn_ok.setVisibility(View.GONE);
            et_short_answer.setVisibility(View.GONE);
            et_answer.setVisibility(View.VISIBLE);
            et_answer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.length() == st) {
                        PrefManager pm = new PrefManager(context);
                        pm.getUserDetails().get("u_id");
                        answerQuestionPresenter.checkShortAnswer(s.toString(), question.getQ_id(), question.getId(), pm.getUserDetails().get("u_id"));

                    }
                }
            });
            et_answer.requestFocus();
        } else {
            et_answer.setVisibility(View.GONE);
            btn_ok.setVisibility(View.VISIBLE);
            et_short_answer.setVisibility(View.VISIBLE);
            PrefManager pm = new PrefManager(context);
            pm.getUserDetails().get("u_id");
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (et_short_answer.getText().length() > 0) {
                        answerQuestionPresenter.checkShortAnswer(et_short_answer.getText().toString(), question.getQ_id(), question.getId(), pm.getUserDetails().get("u_id"));
                    } else
                        Toast.makeText(context, "جواب رو ننوشتی", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    @Override
    public void correcAnswer(int item) {
        Toasty.success(context, "جواب صحیح است", Toast.LENGTH_SHORT, true).show();
        fragmentQuestionsPresenter.removeItem(position);


    }

    @Override
    public void wrongAnswer(int item) {
        Toasty.error(context, "جواب نادرست است", Toast.LENGTH_SHORT, true).show();
        question.setQ_try_num(question.getQ_try_num()+1);
        tv_wrong.setText(String.valueOf(question.getQ_try_num()));
        if(question.getQ_try_num()==2){
            fragmentQuestionsPresenter.removeItem(position);
            finishFrag();
        }
        et_answer.setText(null);
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
    public void setLenght(String s) {
        PinEntryEditText.mNumChars = Integer.parseInt(s);
        initItem(Integer.parseInt(s));
    }

    @Override
    public void showanimation() {

        animation.setVisibility(View.VISIBLE);
        LottieAnimationView lottieAnimationView = animation.findViewById(R.id.lottie_win);
        lottieAnimationView.playAnimation();

    }

    @Override
    public void finishFrag() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1500);
    }


}
