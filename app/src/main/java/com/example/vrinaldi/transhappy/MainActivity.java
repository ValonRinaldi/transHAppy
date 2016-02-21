package com.example.vrinaldi.transhappy;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import ch.schoeb.opendatatransport.IOpenTransportRepository;
import ch.schoeb.opendatatransport.LocalOpenTransportRepository;

public class MainActivity extends AppCompatActivity  {

    private static final Calendar calendar = Calendar.getInstance(Locale.GERMAN);

    //Destination
    private EditText fromParam;
    private EditText toParam;

    //Reverse
    private Button btnReverse;

    //Date
    private DatePicker dpResult;
    private Button btnChangeDate;
    private int year;
    private int month;
    private int day;

    //Time
    private TimePicker tpResult;
    private Button btnChangeTime;
    private int hour;
    private int minute;

    //Departure & Arrival
    private RadioGroup rGrDepArr;
    private RadioButton rBtnDeparture;
    private RadioButton rBtnArrival;
    private boolean isDeparture = true;

    //Search
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromParam = (EditText) findViewById(R.id.searchFromText);
        toParam = (EditText) findViewById(R.id.searchToText);

        btnReverse = (Button) findViewById(R.id.btnReverse);
        btnReverse.setOnClickListener(reverseBtnListener);

        setCurrentDateOnView();
        setCurrentTimeOnView();
        btnChangeTime = (Button) findViewById(R.id.btnChangeTime);
        btnChangeTime.setOnClickListener(changeDateTimeListener);
        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
        btnChangeDate.setOnClickListener(changeDateTimeListener);

        rGrDepArr = (RadioGroup) findViewById(R.id.rGrDepArr);
        rGrDepArr.check(R.id.rbtnDeparture);
        rBtnDeparture = (RadioButton) findViewById(R.id.rbtnDeparture);
        rBtnDeparture.setOnClickListener(radioBtnListener);
        rBtnArrival = (RadioButton) findViewById(R.id.rbtnArrival);
        rBtnArrival.setOnClickListener(radioBtnListener);

        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(searchBtnListener);
    }

    private View.OnClickListener reverseBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tmpFrom = fromParam.getText().toString();
            fromParam.setText(toParam.getText().toString());
            toParam.setText(tmpFrom);
        }
    };

    private void setCurrentDateOnView() {
        dpResult = (DatePicker) findViewById(R.id.dpResult);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dpResult.init(year, month, day, null);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setCurrentTimeOnView() {
        tpResult = (TimePicker) findViewById(R.id.tpResult);
        tpResult.setIs24HourView(true);

        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);

        tpResult.setHour(hour);
        tpResult.setMinute(minute);
    }

    private View.OnClickListener changeDateTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnChangeDate) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this,
                        datePickerListener, year, month, day);
                dpd.show();
            }
            if (v == btnChangeTime) {
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(MainActivity.this, timePickerListener, hour, minute, true);

                tpd.show();
            }
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            dpResult.init(year, month, day, null);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            MainActivity.this.minute = minute;

            tpResult.setHour(hourOfDay);
            tpResult.setMinute(minute);

        }
    };

    private View.OnClickListener radioBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();

            switch(view.getId()) {
                case R.id.rbtnDeparture:
                    if (checked)
                        isDeparture = true;
                    break;
                case R.id.rbtnArrival:
                    if (checked)
                        isDeparture = false;
                    break;
            }
        }
    };

    private View.OnClickListener searchBtnListener = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            SearchParams searchParams = new SearchParams();
            searchParams.setFromParam(fromParam.getText().toString());
            searchParams.setToParam(toParam.getText().toString());
            searchParams.setYear(dpResult.getYear());
            searchParams.setMonth(dpResult.getMonth());
            searchParams.setDay(dpResult.getDayOfMonth());
            searchParams.setHour(tpResult.getHour());
            searchParams.setMinute(tpResult.getMinute());
            searchParams.setIsDeparture(isDeparture);

        /**    Intent intent = new Intent(MainActivity.this, SearchResultsOverview.class);
            intent.putExtra("SearchParams", searchParams.toString());
            startActivity(intent); */
        }
    };
}
