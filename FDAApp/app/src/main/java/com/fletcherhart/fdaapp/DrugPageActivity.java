package com.fletcherhart.fdaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class DrugPageActivity extends AppCompatActivity {
    private static final String EXTRA_DRUG_ID =
            "com.fletcher.fdaapp.drug_id";

    private ViewPager mViewPager;
    private List<Drug> mDrugs;

    public static Intent newIntent(Context packageContext, UUID drugId) {
        Intent intent = new Intent(packageContext, DrugPageActivity.class);
        intent.putExtra(EXTRA_DRUG_ID, drugId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_pager);

        UUID drugId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_DRUG_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_drug_pager_view_pager);

        mDrugs = DrugLab.get(this).getDrugs();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Drug drug = mDrugs.get(position);
                return DrugFragment.newInstance(drug.getId());
            }

            @Override
            public int getCount() {
                return mDrugs.size();
            }
        });

        for (int i = 0; i < mDrugs.size(); i++) {
            if (mDrugs.get(i).getId().equals(drugId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
