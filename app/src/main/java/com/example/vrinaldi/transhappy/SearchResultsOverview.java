package com.example.vrinaldi.transhappy;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vrinaldi.transhappy.utils.ConnectionAdapter;
import com.example.vrinaldi.transhappy.utils.ConnectionWorker;
import com.example.vrinaldi.transhappy.utils.SearchParams;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ch.schoeb.opendatatransport.model.Connection;
import ch.schoeb.opendatatransport.model.ConnectionList;


/**
 * Created by martius on 20.02.16.
 *
 */
public class SearchResultsOverview extends AppCompatActivity {
    public static final int FIRST_CONNECTION = 0;
    public static final int EXTRA_MINUTES = 1;

    private TextView fromValue;
    private TextView toValue;
    private TextView dateValue;
    private TextView timeValue;
    private ListView listView;

    private Button btnEarlier;
    private Button btnLater;

    private ConnectionList conListResult;

    private Connection refConnection;
    private ConnectionList newResult;
    private List<Connection> newConnectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results_overview);

        fromValue = (TextView) findViewById(R.id.fromValue);
        fromValue.setText(SearchParams.from);

        toValue = (TextView) findViewById(R.id.toValue);
        toValue.setText(SearchParams.to);

        dateValue = (TextView) findViewById(R.id.dateValue);
        dateValue.setText(SearchParams.sdf.format(SearchParams.date));

        timeValue = (TextView) findViewById(R.id.timeValue);
        timeValue.setText(SearchParams.stf.format(SearchParams.time));

        listView = (ListView) findViewById(R.id.listView);
        conListResult = createResult();
        listView.setAdapter(new ConnectionAdapter(this, conListResult.getConnections()));

        btnEarlier = (Button) findViewById(R.id.btnEarlier);
        btnEarlier.setOnClickListener(btnEarlierListener);

        btnLater = (Button) findViewById(R.id.btnLater);
        btnLater.setOnClickListener(btnLaterListener);
    };

    private ConnectionList createResult() {
        ConnectionWorker connectionWorker = new ConnectionWorker();
        ConnectionList result = new ConnectionList();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            connectionWorker.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            connectionWorker.execute();
        }

        try {
            result = connectionWorker.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }


    private View.OnClickListener btnEarlierListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            newConnectionList = new ArrayList<>();
            refConnection = conListResult.getConnections().get(FIRST_CONNECTION);
            SearchParams.isArrival = true;
            try {
                SearchParams.time = SearchParams.stf.parse(refConnection.getTo().getArrival().substring(11, 16));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            newResult = createResult();
            newConnectionList = newResult.getConnections();
            if(conListResult.getConnections().size() < 12) {
                newConnectionList.addAll(conListResult.getConnections());
            }
            update();
        }
    };

    private View.OnClickListener btnLaterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            newConnectionList = new ArrayList<>();
            int resSize = conListResult.getConnections().size();
            refConnection = conListResult.getConnections().get(resSize - 1);
            SearchParams.isArrival = false;
            try {
                String timeAsStr = refConnection.getFrom().getDeparture().substring(11, 16);
                Date time = SearchParams.stf.parse(timeAsStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(time);
                cal.add(Calendar.MINUTE, EXTRA_MINUTES);
                SearchParams.time = cal.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            newResult = createResult();
            if(conListResult.getConnections().size() < 12) {
                newConnectionList = conListResult.getConnections();
            }
            newConnectionList.addAll(newResult.getConnections());
            update();
        }
    };

    private void update() {
        conListResult.setConnections(newConnectionList);
        listView.setAdapter(new ConnectionAdapter(SearchResultsOverview.this, conListResult.getConnections()));


        String timestamp = newConnectionList.get(FIRST_CONNECTION).getFrom().getDeparture();
        dateValue.setText(timestamp.substring(8, 10) + "." + timestamp.substring(5, 7) + "." + timestamp.substring(0, 4));
        timeValue.setText(timestamp.substring(11, 16));
    }
};

