package com.fletcherhart.fdaapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by Fletcher on 7/17/2017.
 */

public class DrugFragment extends Fragment {

    private Drug mDrug;
    private TextView mGeneric;
    private static final String ARG_DRUG_ID = "drug_id";


    public static DrugFragment newInstance(UUID drugId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DRUG_ID, drugId);

        DrugFragment fragment = new DrugFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID drugId = (UUID) getArguments().getSerializable(ARG_DRUG_ID);
        mDrug = DrugLab.get(getActivity()).getDrug(drugId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drug, container, false);

        mGeneric = (TextView) v.findViewById(R.id.drug_generic);
        mGeneric.setText(mDrug.getGenericName());


        return v;
    }
}
