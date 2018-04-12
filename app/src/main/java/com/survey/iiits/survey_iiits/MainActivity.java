package com.survey.iiits.survey_iiits;
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
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLayout.openDrawer(Gravity.LEFT);
            }
        });



        dLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.navigation);
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
                    toolbar.setTitle("Send Draft's");
                }
                else if (itemId == R.id.drawer_make_draft)
                {
                    frag = new MakeDraft();
                    toolbar.setTitle("Completed Survey's");
                }
                else if (itemId == R.id.drawer_my_surveys)
                {
                    frag = new My_Survey();
                    toolbar.setTitle("Pending Survey's");
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