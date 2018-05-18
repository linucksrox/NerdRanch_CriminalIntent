package com.dalydays.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment {

    // for receiving data from the caller
    private static final String ARG_HOUR = "hour";
    private static final String ARG_MINUTE = "minute";

    // for sending data back to the caller
    public static final String EXTRA_HOUR = "com.dalydays.android.criminalintent.hour";
    public static final String EXTRA_MINUTE = "com.dalydays.android.criminalintent.minute";

    // local variables
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(int hour, int minute) {
        Bundle args = new Bundle();

        args.putInt(ARG_HOUR, hour);
        args.putInt(ARG_MINUTE, minute);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour = getArguments().getInt(ARG_HOUR);
        int minute = getArguments().getInt(ARG_MINUTE);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        mTimePicker = v.findViewById(R.id.dialog_time_picker);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mTimePicker.setCurrentHour(hour);
            mTimePicker.setCurrentMinute(minute);
        }
        else {
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(minute);
        }

        // Get system preference for 24 hour or 12 hour (AM/PM) view
        mTimePicker.setIs24HourView(DateFormat.is24HourFormat(getContext()));

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // send time back to calling fragment
                        int hour, minute;
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                            hour = mTimePicker.getCurrentHour();
                            minute = mTimePicker.getCurrentMinute();
                        }
                        else {
                            hour = mTimePicker.getHour();
                            minute = mTimePicker.getMinute();
                        }
                        sendResult(Activity.RESULT_OK, hour, minute);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, int hour, int minute) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_HOUR, hour);
        intent.putExtra(EXTRA_MINUTE, minute);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
