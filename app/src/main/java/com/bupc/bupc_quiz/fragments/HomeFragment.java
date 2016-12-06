package com.bupc.bupc_quiz.fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bupc.bupc_quiz.AppClass;
import com.bupc.bupc_quiz.DataHolder;
import com.bupc.bupc_quiz.DatabaseHandler;
import com.bupc.bupc_quiz.MainActivity;
import com.bupc.bupc_quiz.Prefs;
import com.bupc.bupc_quiz.R;
import com.bupc.bupc_quiz.data.models.Question;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private final String TAG = HomeFragment.class.getSimpleName();
    private AppClass mAppClass;
    private Context mContext;
    private DatabaseHandler mDatabaseHandler;
    private View mView;
    private MediaPlayer mStartSound;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        mAppClass = AppClass.getInstance();
        mContext = mAppClass.getAppContext();

        mDatabaseHandler = new DatabaseHandler(mContext);
        mDatabaseHandler.getReadableDatabase();

        File database = mContext.getDatabasePath(mDatabaseHandler.DATABASE_NAME);
        if (false == database.exists()) {
            mDatabaseHandler.getReadableDatabase();
        }

        TextView easyDiff = (TextView) mView.findViewById(R.id.diff_easy),
                moderateDiff = (TextView) mView.findViewById(R.id.diff_moderate),
                difficultDiff = (TextView) mView.findViewById(R.id.diff_difficult);

        easyDiff.setOnClickListener(this);
        moderateDiff.setOnClickListener(this);
        difficultDiff.setOnClickListener(this);

        mStartSound = MediaPlayer.create(mContext, R.raw.start);

        return mView;
    }

    @Override
    public void onClick(View view) {
        setQuestions(view.getId());
        Prefs.setQuestionIndex(0);
        Prefs.setScore(0);

        if (Prefs.isSoundActive())
            mStartSound.start();

        Fragment frag = new QuestionsFragment();
        ((MainActivity) getActivity()).switchFragment(frag);
    }

    private void setQuestions(int viewId) {
        List<Question> questions = new ArrayList<>();
        List<Question> allQuestions = mDatabaseHandler.getQuestions();
        String difficulty = "";

        switch (viewId) {
            case R.id.diff_easy:
                difficulty = "easy";
                break;
            case R.id.diff_moderate:
                difficulty = "moderate";
                break;
            case R.id.diff_difficult:
                difficulty = "difficult";
                break;
        }
        for (Question question : allQuestions) {
            if (question.getDifficulty().equals(difficulty)) {
                questions.add(question);
            }
        }

        DataHolder.getInstance().setQuestions(questions);
    }
}