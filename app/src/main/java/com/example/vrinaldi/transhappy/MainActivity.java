package com.example.vrinaldi.transhappy;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.vrinaldi.transhappy.utils.SearchParams;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ch.schoeb.opendatatransport.IOpenTransportRepository;
import ch.schoeb.opendatatransport.OnlineOpenTransportRepository;
import ch.schoeb.opendatatransport.model.Station;
import ch.schoeb.opendatatransport.model.StationList;

public class MainActivity extends AppCompatActivity  {

    private static final Calendar calendar = Calendar.getInstance(Locale.GERMAN);

    //Destination
    private AutoCompleteTextView from;
    public AutoCompleteTextView to;

    public int adapterOnView = 0;
    private int actingAsyncTasks;
    private String searchString;

    //Reverse
    private Button btnReverse;

    //Date
    private DatePicker dpResult;
    private Button btnChangeDate;
    private Date date;

    //Time
    private TimePicker tpResult;
    private Button btnChangeTime;
    private Date time;

    //Departure & Arrival
    private RadioGroup rGrDepArr;
    private RadioButton rBtnDeparture;
    private RadioButton rBtnArrival;

    private boolean isArrival;

    //Search
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFromInputField();
        setToInputField();

        btnReverse = (Button) findViewById(R.id.btnReverse);
        btnReverse.setOnClickListener(reverseBtnListener);

        setDateBtn();
        setTimeButton();
        setArrDepRadioBtn();

        btnSearch = (Button) findViewById(R.id.searchButton);
        btnSearch.setOnClickListener(searchBtnListener);
    }

    private void setArrDepRadioBtn() {
        rGrDepArr = (RadioGroup) findViewById(R.id.rGrDepArr);
        rGrDepArr.check(R.id.rbtnDeparture);
        rBtnDeparture = (RadioButton) findViewById(R.id.rbtnDeparture);
        rBtnDeparture.setOnClickListener(radioBtnListener);
        rBtnArrival = (RadioButton) findViewById(R.id.rbtnArrival);
        rBtnArrival.setOnClickListener(radioBtnListener);
    }

    private void setFromInputField() {
        from = (AutoCompleteTextView) findViewById(R.id.fromStation);
        from.setText(SearchParams.from);
        from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapterOnView = 1;
                retrieveStationList(s, start, before, count);
            }
            @Override
            public void afterTextChanged(Editable s) {
                SearchParams.from = s.toString();
            }
        });
    }

    private void setToInputField() {
        to = (AutoCompleteTextView) findViewById(R.id.toStation);
        to.setText(SearchParams.to);
        to.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapterOnView = 2;
                retrieveStationList(s, start, before, count);
            }
            @Override
            public void afterTextChanged(Editable s) {
                SearchParams.to = s.toString();
            }
        });
    }

    private void retrieveStationList(CharSequence s, int start, int before, int count) {
        searchString = s.toString();
        actingAsyncTasks++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
           new StationListRetriever().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
           new StationListRetriever().execute();
        }
    }

    private View.OnClickListener reverseBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tmpFrom = from.getText().toString();
            from.setText(to.getText().toString());
            to.setText(tmpFrom);
        }
    };

    private void setDateBtn() {
        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
        btnChangeDate.setOnClickListener(changeDateTimeListener);

        dpResult = (DatePicker) findViewById(R.id.dpResult);
        date = new Date();
        dpResult.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setTimeButton() {
        btnChangeTime = (Button) findViewById(R.id.btnChangeTime);
        btnChangeTime.setOnClickListener(changeDateTimeListener);

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
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this,
                        datePickerListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
            if (v == btnChangeTime) {
                TimePickerDialog tpd = new TimePickerDialog(MainActivity.this, timePickerListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                tpd.show();
            }
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            try {
                date = SearchParams.sdf.parse(selectedDay + "." + selectedMonth + "." + selectedYear);
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
                time = SearchParams.stf.parse(hourOfDay + ":" + minute);
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
            if(from.getText().toString().isEmpty()) {
                showErrorMessage(String.valueOf(R.string.missingFrom));
                return;
            }
            if(to.getText().toString().isEmpty()) {
                showErrorMessage(R.string.missingTo);
                return;
            }

            SearchParams.from = from.getText().toString();
            SearchParams.to = to.getText().toString();
            SearchParams.date = date;
            SearchParams.time = time;
            SearchParams.isArrival = isArrival;

            Intent intent = new Intent(MainActivity.this, SearchResultsOverview.class);
            startActivity(intent);
        }
    };

    private void showErrorMessage(String errorMsg) {
        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
    }

    public class StationListRetriever extends AsyncTask<Void, Void, Void> {
        private Exception exception = new Exception();
        private ArrayAdapter arrAdapter;
        private List<String> strStations = new ArrayList<String>();;
        public IOpenTransportRepository repo = new OnlineOpenTransportRepository();

        @Override
        protected Void doInBackground(Void... params) {
            strStations.clear();

            try
            {
                this.exception = null;
                StationList sl = repo.findStations(searchString);

                if (sl.getStations().size() > 0) {
                    for (Station s : sl.getStations()) {
                        strStations.add(s.getName());
                    }
                }
            }
            catch(Exception e){
                this.exception = e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(exception != null){
                showMessage(exception.getLocalizedMessage());
                return;
            }

            if (actingAsyncTasks == 1) {
                arrAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>(strStations));
                if (adapterOnView == 1) {
                    from.setAdapter(arrAdapter);
                } else if (adapterOnView == 2) {
                    to.setAdapter(arrAdapter);
                }
                arrAdapter.notifyDataSetChanged();
                actingAsyncTasks--;
            } else {
                actingAsyncTasks--;
            }
        }

        private void showMessage( String msg) {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
