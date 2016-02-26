package com.example.vrinaldi.transhappy;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vrinaldi.transhappy.utils.ConnectionAdapter;
import com.example.vrinaldi.transhappy.utils.ConnectionWorker;
import com.example.vrinaldi.transhappy.utils.SearchParams;

import java.util.concurrent.ExecutionException;

import ch.schoeb.opendatatransport.model.ConnectionList;


/**
 * Created by martius on 20.02.16.
 */
public class SearchResultsOverview extends AppCompatActivity {

    private TextView fromValue;
    private TextView toValue;
    private TextView dateValue;
    private TextView timeValue;
    private ListView listView;


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
        listView.setAdapter(new ConnectionAdapter(this, createResult().getConnections()));
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
};

