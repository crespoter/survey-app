package com.survey.iiits.survey_iiits;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class surveyPreference extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_preference);
        final String draft_id = getIntent().getStringExtra("draft_id");
        Button submitButton = (Button) findViewById(R.id.surveyPreferenceSubmit);
        submitButton.setOnClickListener( new View.OnClickListener()
        {
            public void onClick (View v){
                CheckBox anonymousCheckbox,forcedResponseCheckbox;
                anonymousCheckbox = (CheckBox) findViewById(R.id.anonymousCheckbox);
                forcedResponseCheckbox = (CheckBox) findViewById(R.id.forcedResponseCheckbox);
                String anonymous = anonymousCheckbox.isChecked()?"1":"0";
                String forcedResponse = forcedResponseCheckbox.isChecked()?"1":"0";
                Intent viewGroups = new Intent(v.getContext(),SelectGroup.class);

                viewGroups.putExtra("draft_id",draft_id);
                viewGroups.putExtra("anonymous",anonymous);
                viewGroups.putExtra("forcedResponse",forcedResponse);
                startActivity(viewGroups);
            }
        });
    }

}
