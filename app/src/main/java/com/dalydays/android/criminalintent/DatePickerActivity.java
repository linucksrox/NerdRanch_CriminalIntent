package com.dalydays.android.criminalintent;

import android.support.v4.app.Fragment;

public class DatePickerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DatePickerFragment();
    }
}
