package com.dalydays.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private Button mJumpToFirstButton;
    private Button mJumpToLastButton;
    private int lastIndex;
    private static final String EXTRA_CRIME_ID = "com.dalydays.android.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crime_pager);

        // Hook up ViewPager with fragments
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = findViewById(R.id.crime_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                toggleButtons();
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        lastIndex = mCrimes.size() - 1;

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        // Hook up previous and next buttons
        mJumpToFirstButton = findViewById(R.id.jump_to_first_button);
        mJumpToLastButton = findViewById(R.id.jump_to_last_button);

        mJumpToFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0, true);
                toggleButtons();
            }
        });

        mJumpToLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(lastIndex, true);
                toggleButtons();
            }
        });
    }

    private void toggleButtons() {
        if (mViewPager.getCurrentItem() == 0) {
            mJumpToFirstButton.setEnabled(false);
        }
        else {
            mJumpToFirstButton.setEnabled(true);
        }

        if (mViewPager.getCurrentItem() == lastIndex) {
            mJumpToLastButton.setEnabled(false);
        }
        else {
            mJumpToLastButton.setEnabled(true);
        }
    }
}
