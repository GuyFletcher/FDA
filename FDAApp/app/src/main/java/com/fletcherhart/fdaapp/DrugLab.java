package com.fletcherhart.fdaapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Fletcher on 7/10/2017.
 */

public class DrugLab {
    private static DrugLab sDrugLab;

    private Context mContext;
    private ArrayList<Drug> mDrugs;

    public DrugLab(Context context) {
        mDrugs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Drug drug = new Drug();
            drug.setGenericName("Drug #" + i);
            mDrugs.add(drug);
        }
    }

    public static DrugLab get(Context context) {
        if (sDrugLab == null) {
            sDrugLab = new DrugLab(context);
        }
        return sDrugLab;
    }

    public List<Drug> getDrugs() {
        return mDrugs;
    }

    public Drug getDrug(UUID id) {
        for (Drug drug : mDrugs) {
            if (drug.getId().equals(id)) {
                return drug;
            }
        }
        return null;
    }

}
