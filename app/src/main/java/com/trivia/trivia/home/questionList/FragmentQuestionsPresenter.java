package com.trivia.trivia.home.questionList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.trivia.R;
import com.trivia.trivia.base.myFragment;
import com.trivia.trivia.home.gameActivity.answerQuestion.MultipleChoiceFragment;
import com.trivia.trivia.home.gameActivity.answerQuestion.ShortAnswerFragment;
import com.trivia.trivia.util.Question;
import com.trivia.trivia.webservice.connectToServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentQuestionsPresenter {
    Iquestionview fragment_event;

    FragmentQuestionsPresenter(Iquestionview e) {
        this.fragment_event = e;
    }

    public void refreshEventList() {
        connectToServer.getQuestionsList(this);
        fragment_event.showLoading();
    }

    public void reciveRequeset(String response) throws JSONException {
        fragment_event.hideLoading();
        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        ArrayList<Question> questions2 = new ArrayList<>();
        final Question[] questions = gson.fromJson(obj.getString("questions"), Question[].class);

        for (int i = 0; i < questions.length; i++) {

            questions2.add(questions[i]);

        }
        fragment_event.setItems(questions2);

    }

    public void selectQuestion(Question q){
        Fragment fragment = null;
        switch (q.getQ_type()){

            case "MultipleChoice":
                 fragment= MultipleChoiceFragment.newInstance(q);
                break;
            case "ShortAnswer":
                fragment= ShortAnswerFragment.newInstance(q);
                break;
            case "Descriptive":
                break;
        }
        loadStartedFragment(fragment);
    }
    private void loadStartedFragment(Fragment fragment) {
        // load fragment
        //((myFragment) fragment_event)
      //  Fragment fragment=Fragment_main_event.newInstance(e);
      //  Fragment fragment=Fragment_main_event.newInstance(e);
        FragmentTransaction transaction = ((myFragment) fragment_event).getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
