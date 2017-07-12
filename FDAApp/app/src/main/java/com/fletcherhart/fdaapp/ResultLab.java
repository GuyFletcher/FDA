package com.fletcherhart.fdaapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Fletcher on 7/10/2017.
 */

public class ResultLab {
    private static ResultLab sResultLab;

    private Context mContext;

    public static ResultLab get(Context context) {
        if (sResultLab == null) {
            sResultLab = new ResultLab(context);
        }
        return sResultLab;
    }

    private ResultLab(Context context) {
        mContext = context.getApplicationContext();
    }

    public List<Drug> getResults() {
        List<Drug> drugs = new ArrayList<>();

        return drugs;
    }

    public Drug getResult(UUID id) {
       Drug drug = new Drug();

        return drug;
    }

}
