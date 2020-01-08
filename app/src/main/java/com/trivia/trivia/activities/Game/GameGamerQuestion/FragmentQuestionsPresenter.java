package com.trivia.trivia.activities.Game.GameGamerQuestion;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.activities.Game.GameAnswerQuestion.DescAnswerAlert.DescAnswerAlert;

import com.trivia.trivia.activities.Game.GameAnswerQuestion.MultipleChoiceAlert.MultipleChoiceAlert;
import com.trivia.trivia.activities.Game.GameAnswerQuestion.ShortAnswerAlert.ShortAnswerAlert;
import com.trivia.trivia.base.BaseFragment;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentQuestionsPresenter {
    Iquestionview fragment_question_list;

    FragmentQuestionsPresenter(Iquestionview e) {
        this.fragment_question_list = e;
    }

    public void refreshEventList(int page) {
        connectToServer.getQuestionsList(this, page);
        fragment_question_list.showLoading();
    }

    public void removeItem(int i) {
        fragment_question_list.removeitem(i);
    }

    public void reciveRequeset(String response) throws JSONException {
        fragment_question_list.hideLoading();
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        ArrayList<Question> questions2 = new ArrayList<>();
        final Question[] questions = gson.fromJson(obj.getString("questions"), Question[].class);
        questions2.addAll(Arrays.asList(questions));
        fragment_question_list.setItems(questions2);
    }

    public void selectQuestion(Question q, int position) {
        Fragment fragment = null;
        switch (q.getQ_type()) {

            case "MultipleChoice":

                MultipleChoiceAlert multipleChoiceAlert = new MultipleChoiceAlert();
                multipleChoiceAlert.init_dialog(((BaseFragment) fragment_question_list).getActivity(), q, this, position);
                multipleChoiceAlert.show();
                // fragment= GameMultipleChoiceFragment.newInstance(q);
                break;
            case "ShortAnswer":
                // fragment= GameShortAnswerFragment.newInstance(q);

                ShortAnswerAlert shortAnswerAlert = new ShortAnswerAlert();
                shortAnswerAlert.init_dialog(((BaseFragment) fragment_question_list).getActivity(), q, this, position);
                shortAnswerAlert.show();
                break;
            case "DescAnswer":

                DescAnswerAlert descAnswerAlert = new DescAnswerAlert();
                descAnswerAlert.init_dialog(((BaseFragment) fragment_question_list).getActivity(), q, this, position);
                descAnswerAlert.show();

                break;
        }
    }

    public void sellQuestion(Question q,String price,int position){
connectToServer.sellQuestion(this,q.getId(),q.getQ_id(),price,position);

    }
    public void sellRequeset(String response,int position){
        removeItem(position);
        fragment_question_list.dimissSellAlert();

    }


}
