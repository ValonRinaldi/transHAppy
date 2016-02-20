package com.example.vrinaldi.transhappy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by martius on 20.02.16.
 */
public class SearchResultsOverview extends AppCompatActivity {

    //Declare View variable
    private TextView mArr;
    private TextView mFrom;
    private Button mResults_DetailButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_overview);

        //Assign view from the layoutfile to the corresponding variables
        final TextView mArr = (TextView) findViewById(R.id.results_Arr);
        final TextView mFrom = (TextView) findViewById(R.id.results_From);
        Button mResults_DetailButton = (Button) findViewById(R.id.results_DetailsButton);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Final Goal: Change to the reseultdetail activity for the selected connections

                //To test button:
                String testString = "From: Basel";
                mFrom.setText(testString);
                Intent intent = new Intent(SearchResultsOverview.this, SearchResultsDetail.class);
                startActivity(intent);

            }

            ;


        };

        mResults_DetailButton.setOnClickListener(listener);


    };
};

