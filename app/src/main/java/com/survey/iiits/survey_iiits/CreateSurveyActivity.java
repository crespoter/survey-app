package com.survey.iiits.survey_iiits;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


public class CreateSurveyActivity extends AppCompatActivity {
    createSurveyAdapter adapter;
    RecyclerView recyclerView;
    DrawerLayout dLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createsurvey);

        recyclerView = (RecyclerView)findViewById(R.id.createSurvey_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new createSurveyAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        FloatingActionButton addButton = (FloatingActionButton)findViewById(R.id.createSurvey_addIcon);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HHERE", "onClick:CLICKED FOB");
                adapter.addQuestion();
                recyclerView.setAdapter(adapter);
            }
        });
    }

}