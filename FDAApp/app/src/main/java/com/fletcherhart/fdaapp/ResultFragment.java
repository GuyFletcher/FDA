package com.fletcherhart.fdaapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.UUID;

/**
 * Created by Fletcher on 7/12/2017.
 */

public class ResultFragment extends Fragment {

    private static final String ARG_RESULT_ID = "result_id";

    private Drug mDrug;

    public static ResultFragment newInstance(UUID resultId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESULT_ID, resultId);

        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID resultId = (UUID) getArguments().getSerializable(ARG_RESULT_ID);
        mDrug = ResultLab.get(getActivity()).getResult(resultId);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);




        return v;
    }

}
