package com.bupc.bupc_quiz;

import com.bupc.bupc_quiz.data.models.Question;

import java.util.ArrayList;
import java.util.List;


public class DataHolder {

    private List<Question> mQuestions = new ArrayList<>();
    private static final DataHolder mInstance = new DataHolder();

    public static synchronized DataHolder getInstance() {
        return mInstance;
    }

    public void addQuestion(Question question) {
        mQuestions.add(question);
    }

    public void addQuestions(List<Question> Questions) {
        mQuestions.addAll(Questions);
    }

    public void setQuestions(List<Question> Questions) {
        mQuestions.clear();
        addQuestions(Questions);
    }

    public Question getQuestion(int index) {
        return mQuestions.get(index);
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public int getTotalItems() {
        return mQuestions.size();
    }

}
