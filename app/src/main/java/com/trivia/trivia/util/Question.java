package com.trivia.trivia.util;

public class Question {
    private String Q_title;
    private String Q_type;
    private String Q_lang;
    private String Q_id;
    private String Q_img;



    public Question(String Q_title, String Q_type, String Q_lang, String Q_id, String Q_img) {
        setQ_title(Q_title);
        setQ_type(Q_type);
        setQ_lang(Q_lang);
        setQ_id(Q_id);
        setQ_img(Q_img);


    }


    public String getQ_title() {
        return Q_title;
    }

    public void setQ_title(String q_title) {
        Q_title = q_title;
    }

    public String getQ_type() {
        return Q_type;
    }

    public void setQ_type(String q_type) {
        Q_type = q_type;
    }

    public String getQ_lang() {
        return Q_lang;
    }

    public void setQ_lang(String q_lang) {
        Q_lang = q_lang;
    }

    public String getQ_id() {
        return Q_id;
    }

    public void setQ_id(String q_id) {
        Q_id = q_id;
    }

    public String getQ_img() {
        return Q_img;
    }

    public void setQ_img(String q_img) {
        Q_img = q_img;
    }
}
