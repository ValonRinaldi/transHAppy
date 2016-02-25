package com.example.vrinaldi.transhappy;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
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

import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    private Date date;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    //Time
    private TimePicker tpResult;
    private Button btnChangeTime;

    private Date time;
    SimpleDateFormat stf = new SimpleDateFormat("HH:mm");

    //Departure & Arrival
    private RadioGroup rGrDepArr;
    private RadioButton rBtnDeparture;
    private RadioButton rBtnArrival;
    private boolean isArrival;

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
        date = new Date();
        dpResult.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setCurrentTimeOnView() {
        tpResult = (TimePicker) findViewById(R.id.tpResult);
        tpResult.setIs24HourView(true);

        time = new Date();
        tpResult.setHour(calendar.get(Calendar.HOUR));
        tpResult.setMinute(calendar.get(Calendar.MINUTE));
    }

    private View.OnClickListener changeDateTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnChangeDate) {
                //TODO set Date
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this,
                        datePickerListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
            if (v == btnChangeTime) {
                //TODO: set time
                TimePickerDialog tpd = new TimePickerDialog(MainActivity.this, timePickerListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                tpd.show();
            }
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            try {
                date = sdf.parse(selectedDay + "." + selectedMonth + "." + selectedYear);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dpResult.init(selectedYear, selectedMonth, selectedDay, null);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            try {
                time = stf.parse(hourOfDay + ":" + minute);
            } catch (ParseException e) {
                e.printStackTrace();
            }

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
                        isArrival = false;
                    break;
                case R.id.rbtnArrival:
                    if (checked)
                        isArrival = true;
                    break;
            }
        }
    };


    private View.OnClickListener searchBtnListener = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            if(fromParam.getText().toString().isEmpty()) {
                showErrorMessage("Bitte Abfahrtsort eingeben");
                return;
            }
            if(toParam.getText().toString().isEmpty()) {
                showErrorMessage("Bitte Zielort eingeben");
                return;
            }

            SearchParams searchParams = new SearchParams();
            searchParams.setFromParam(fromParam.getText().toString());
            searchParams.setToParam(toParam.getText().toString());
            searchParams.setDate(date);
            searchParams.setTime(time);
            searchParams.setIsArrival(isArrival);

            Intent intent = new Intent(MainActivity.this, SearchResultsOverview.class);
            intent.putExtra("SearchParams", searchParams.toString());
            startActivity(intent);
        }
    };

    private void showErrorMessage(String errorMsg) {
        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
    }
}
