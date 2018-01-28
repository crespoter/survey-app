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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLayout.openDrawer(Gravity.LEFT);
            }
        });



        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                }
                else if (itemId == R.id.drawer_send_survey)
                {
                    frag = new SendSurvey();
                }
                else if (itemId == R.id.drawer_make_draft)
                {
                    frag = new MakeDraft();
                }
                else if (itemId == R.id.drawer_my_surveys)
                {
                    frag = new My_Survey();
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
    }
}