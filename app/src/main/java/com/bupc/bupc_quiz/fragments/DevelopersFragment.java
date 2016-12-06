package com.bupc.bupc_quiz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bupc.bupc_quiz.R;

public class DevelopersFragment extends Fragment {

    private final String TAG = DevelopersFragment.class.getSimpleName();
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_developers, container, false);

        return mView;
    }
}
