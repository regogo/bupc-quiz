package com.bupc.bupc_quiz.fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bupc.bupc_quiz.AppClass;
import com.bupc.bupc_quiz.DataHolder;
import com.bupc.bupc_quiz.MainActivity;
import com.bupc.bupc_quiz.Prefs;
import com.bupc.bupc_quiz.R;
import com.bupc.bupc_quiz.data.models.Question;


public class QuestionsFragment extends Fragment implements View.OnClickListener {

    private static final int TIME_LIMIT = 10;

    private final String TAG = QuestionsFragment.class.getSimpleName();
    private View mView;
    private AppClass mAppClass;
    private Context mContext;
    private DataHolder mDataHolder;

    private TextView mScoreText, mTimeText, mQuestion, mOption1, mOption2, mOption3, mOption4;
    private int mScore = 0, mTime = 10, mQuestionCtr = 0;
    private String mAnswer;

    private MediaPlayer mCorrectSound, mIncorrectSound;


    private CountDownTimer mTimeCountdown = new CountDownTimer(TIME_LIMIT * 1000, 1000) {
        int secs = 0;

        @Override
        public void onTick(long millisUntilFinished) {
            secs = (int) (millisUntilFinished / 1000);
            mTimeText.setText(secs + "s");
        }

        @Override
        public void onFinish() {
            mTimeText.setText(0 + "s");
            mAppClass.toaster("Time's up!");
            questionDone();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_questions, container, false);
        mAppClass = AppClass.getInstance();
        mContext = mAppClass.getAppContext();
        mDataHolder = DataHolder.getInstance();

        mScoreText = (TextView) mView.findViewById(R.id.score);
        mTimeText = (TextView) mView.findViewById(R.id.time);
        mQuestion = (TextView) mView.findViewById(R.id.question);
        mOption1 = (TextView) mView.findViewById(R.id.option1);
        mOption2 = (TextView) mView.findViewById(R.id.option2);
        mOption3 = (TextView) mView.findViewById(R.id.option3);
        mOption4 = (TextView) mView.findViewById(R.id.option4);

        mScore = Prefs.getScore();
        mScoreText.setText(mScore + "");
        mTimeText.setText(mTime + "s");

        listenersToggle(true);
        updateQuestion();

        mCorrectSound = MediaPlayer.create(mContext, R.raw.correct);
        mIncorrectSound = MediaPlayer.create(mContext, R.raw.incorrect);

        return mView;
    }

    private void listenersToggle(boolean on) {
        if (on) {
            mOption1.setOnClickListener(this);
            mOption2.setOnClickListener(this);
            mOption3.setOnClickListener(this);
            mOption4.setOnClickListener(this);
        } else {
            mOption1.setClickable(false);
            mOption2.setClickable(false);
            mOption3.setClickable(false);
            mOption4.setClickable(false);
        }
    }

    private void updateQuestion() {
        mTimeCountdown.start();
        mQuestionCtr = Prefs.getQuestionIndex();
        Question question = mDataHolder.getQuestion(mQuestionCtr);

        mQuestion.setText(question.getQuestion());
        mOption1.setText(question.getOption1());
        mOption2.setText(question.getOption2());
        mOption3.setText(question.getOption3());
        mOption4.setText(question.getOption4());
        mAnswer = question.getAnswer();

        Prefs.setQuestionIndex(mQuestionCtr + 1);
    }

    @Override
    public void onClick(View view) {
        questionDone();
        mTimeCountdown.cancel();
        String selected = ((TextView) view).getText().toString();

        if (selected.equals(mAnswer)) {
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.Green));
            mScore++;
            mScoreText.setText(mScore + "");
            Prefs.setScore(mScore);
            if (Prefs.isSoundActive())
                mCorrectSound.start();
        } else {
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.Red));
            if (Prefs.isSoundActive())
                mIncorrectSound.start();
        }

    }

    private void questionDone() {
        listenersToggle(false);
        if (mQuestionCtr == mDataHolder.getTotalItems() - 1) {
            jumpTo(new FinishedFragment());
        } else {
            jumpTo(new QuestionsFragment());
        }
    }

    private void jumpTo(final Fragment frag) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((MainActivity) getActivity()).switchFragment(frag);
            }
        }, 1500);
    }
}