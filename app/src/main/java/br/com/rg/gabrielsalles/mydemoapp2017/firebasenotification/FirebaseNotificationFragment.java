package br.com.rg.gabrielsalles.mydemoapp2017.firebasenotification;


import android.app.DatePickerDialog;

import java.text.DateFormat;
import java.util.Calendar;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import br.com.rg.gabrielsalles.mydemoapp2017.R;

public class FirebaseNotificationFragment extends Fragment implements View.OnClickListener {

    private EditText mDateET;
    private EditText mTimeET;
    private EditText mNotificationET;
    private CardView mSendCV;
    private Calendar mCalendar = Calendar.getInstance();
    private DateFormat mDateFormat;
    private DateFormat mTimeFormat;
    private boolean mIs24HourFormat;

    public FirebaseNotificationFragment() {
        // Required empty public constructor
    }

    public static FirebaseNotificationFragment newInstance() {
        return new FirebaseNotificationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        mTimeFormat = android.text.format.DateFormat.getTimeFormat(getContext());
        mIs24HourFormat = android.text.format.DateFormat.is24HourFormat(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.firebase_notification_fragment, container, false);
        mDateET = (EditText) v.findViewById(R.id.fn_date_text);
        mTimeET = (EditText) v.findViewById(R.id.fn_time_text);
        mSendCV = (CardView) v.findViewById(R.id.fn_send_notification);
        mNotificationET = (EditText) v.findViewById(R.id.fn_notification_input_Text);
        mDateET.setOnClickListener(this);
        mTimeET.setOnClickListener(this);
        mSendCV.setOnClickListener(this);
        mDateET.setText(mDateFormat.format(mCalendar.getTime()));
        mTimeET.setText(mTimeFormat.format(mCalendar.getTime()));
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fn_date_text:
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, month);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        mDateET.setText(mDateFormat.format(mCalendar.getTime()));
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.fn_time_text:
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        mCalendar.set(Calendar.MINUTE, minute);

                        mTimeET.setText(mTimeFormat.format(mCalendar.getTime()));
                    }
                }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), mIs24HourFormat).show();
                break;

            case R.id.fn_send_notification:
                if (allFieldsAreCorrect()) {
                    makeSnackbar("Everything all right");
                }
                break;
        }
    }

    private boolean allFieldsAreCorrect() {
        return allFieldsAreFilledIn() && dateIsCorrect();
    }

    private boolean allFieldsAreFilledIn() {
        return (mDateET.getText().toString().trim().length() > 0) &&
                (mTimeET.getText().toString().trim().length() > 0) &&
                (mNotificationET.getText().toString().trim().length() > 0)
                ||
                makeSnackbarWithBooleanReturn(getString(R.string.fn_all_fields_required), false);
    }

    private boolean dateIsCorrect() {
        return mCalendar.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()
                ||
                makeSnackbarWithBooleanReturn(getString(R.string.fn_wrong_date), false);
    }

    private boolean makeSnackbarWithBooleanReturn(String text, boolean ret) {
        makeSnackbar(text);
        return ret;
    }

    private void makeSnackbar(String text) {
        Snackbar.make(getView(), text, Snackbar.LENGTH_LONG).show();
    }
}
