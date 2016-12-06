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
import com.bupc.bupc_quiz.MainActivity;
import com.bupc.bupc_quiz.Prefs;
import com.bupc.bupc_quiz.R;

public class FinishedFragment extends Fragment {

    private final String TAG = FinishedFragment.class.getSimpleName();
    private AppClass mAppClass;
    private Context mContext;
    private View mView;

    private TextView mScore, mRemarks, mDone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_finished, container, false);
        mAppClass = AppClass.getInstance();
        mContext = mAppClass.getAppContext();

        mScore = (TextView) mView.findViewById(R.id.score);
        mRemarks = (TextView) mView.findViewById(R.id.remarks);
        mDone = (TextView) mView.findViewById(R.id.done);

        int score = Prefs.getScore(),
                totalItems = DataHolder.getInstance().getTotalItems();

        mScore.setText(score + "/" + totalItems);

        Double grade = ((double) score / totalItems) * 100;
        Boolean didPass = grade > 60;
        String remark = (didPass) ? "You Passed!" : "You Failed!";
        mRemarks.setText(remark);

        setupSound(didPass);

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new HomeFragment();
                ((MainActivity) getActivity()).switchFragment(frag);

            }
        });

        return mView;
    }

    private void setupSound(Boolean didPass) {
        if (!Prefs.isSoundActive())
            return;

        MediaPlayer finishedSound;
        if (didPass)
            finishedSound = MediaPlayer.create(mContext, R.raw.passed);
        else
            finishedSound = MediaPlayer.create(mContext, R.raw.failed);
        finishedSound.start();
    }
}
