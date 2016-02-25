package com.example.vrinaldi.transhappy;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import ch.schoeb.opendatatransport.IOpenTransportRepository;
import ch.schoeb.opendatatransport.OpenTransportRepositoryFactory;
import ch.schoeb.opendatatransport.model.ConnectionList;


/**
 * Created by martius on 20.02.16.
 */
public class SearchResultsOverview extends AppCompatActivity {

    //Declare View variable
    private TextView mArr;
    private TextView mFrom;
    private Button mResults_DetailButton;


   private SearchParams searchParams = new SearchParams();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_overview);

        Intent intent = getIntent();
        String data = intent.getStringExtra("SearchParams");
        try {
            searchParams = SearchParams.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ConnectionWorker connectionWorker = new ConnectionWorker();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            connectionWorker.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, searchParams);
        } else {
            connectionWorker.execute(searchParams);
        }

        ConnectionList result = null;

        try {
            result = connectionWorker.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ConnectionAdapter connectionAdapter = new ConnectionAdapter(this, result.getConnections());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(connectionAdapter);


        /** //Assign view from the layoutfile to the corresponding variables
        TextView mFrom = (TextView) findViewById(R.id.results_From);
        TextView mArr = (TextView) findViewById(R.id.results_Arr);
        Button mResults_DetailButton = (Button) findViewById(R.id.results_DetailsButton); */

    };
};

