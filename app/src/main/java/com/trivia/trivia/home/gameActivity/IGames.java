package com.trivia.trivia.home.gameActivity;

import com.trivia.trivia.util.MultipleChoicePage;

public interface IGames {
    void correcAnswer(int item);
    void wrongAnswer(int item);
    void setScore();
    void setItems(MultipleChoicePage mpcp);

}
