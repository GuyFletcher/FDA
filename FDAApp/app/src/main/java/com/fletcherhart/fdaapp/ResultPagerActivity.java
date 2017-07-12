package com.fletcherhart.fdaapp;

/**
 * Created by Fletcher on 7/10/2017.
 */
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

public class ResultPagerActivity extends AppCompatActivity {
    private static final String EXTRA_Result_ID =
            "com.bignerdranch.android.criminalintent.Result_id";

    private ViewPager mViewPager;
    private List<Drug> mDrugs;

    public static Intent newIntent(Context packageContext, UUID ResultId) {
        Intent intent = new Intent(packageContext, ResultPagerActivity.class);
        intent.putExtra(EXTRA_Result_ID, ResultId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_pager);

        UUID ResultId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_Result_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_result_pager_view_pager);

        mDrugs = ResultLab.get(this).getResults();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Drug drug = mDrugs.get(position);
                return ResultFragment.newInstance(drug.getId());
            }

            @Override
            public int getCount() {
                return mDrugs.size();
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                Drug Drug = mDrugs.get(position);
                if (Drug.getGenericName() != null) {
                    setTitle(Drug.getGenericName());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        for (int i = 0; i < mDrugs.size(); i++) {
            if (mDrugs.get(i).getId().equals(ResultId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
