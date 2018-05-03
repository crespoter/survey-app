/*
Authors : [David Christie,Shyam Sunder]
Last Edited : 4/12/2018
 */
/*
SHYAM SYNTAX:
MAKE DRAFT IS COMPLETED SURVEYS,
 */
package com.survey.iiits.survey_iiits;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    DrawerLayout dLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLayout.openDrawer(Gravity.LEFT);
            }
        });
        Intent serviceIntent = new Intent(getBaseContext(), NewSurveyCheckerService.class);
        startService(serviceIntent);


        dLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        Fragment def = new Home();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, def);
        transaction.commit();
        dLayout.closeDrawers();
        
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment frag = null;
                int itemId = menuItem.getItemId();
                if (itemId == R.id.drawer_home)
                {
                    frag = new Home();
                    toolbar.setTitle("Home");
                }
                else if (itemId == R.id.drawer_send_survey)
                {
                    frag = new SendSurvey();
                    toolbar.setTitle("Send Drafts");
                }
                else if (itemId == R.id.drawer_make_draft)
                {
                    frag = new Completed_Surveys();
                    toolbar.setTitle("Completed Survey's");
                }
                else if (itemId == R.id.drawer_my_surveys)
                {
                    frag = new Pending_Survey();
                    toolbar.setTitle("Pending Surveys");
                }
                if (frag != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, frag);
                    transaction.commit();
                    dLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });
        // init SwipeRefreshLayout and ListView
       }

}