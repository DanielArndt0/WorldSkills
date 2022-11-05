package com.app.speedrun;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimerPicker extends DialogFragment {
    TimePickerDialog.OnTimeSetListener listener;
    Calendar calendar;

    public TimerPicker(TimePickerDialog.OnTimeSetListener listener, Calendar calendar) {
        this.listener = listener;
        this.calendar = calendar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), listener, hour, minute, true);
    }
}
