package com.survey.iiits.survey_iiits;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.LoginFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
 * Authors : [David Christie,Shyam Sunder]
 * Last Edited : 4/10/2018
 * Project : Pen Survey
 * Description : Survey application for IIIT Sricity
 */

public class Questionaire extends Activity {
    ArrayList<Model> list2;
  String inputLine;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private RecyclerView mRecyclerView;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private RequestQueue mRequestQueue,mRequestQueue2;
    private StringRequest mStringRequest,mStringRequest2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionaire);
        Intent myIntent = getIntent();
        String questionnaireId = myIntent.getStringExtra("firstKeyName");
        Intent intent = new Intent(Questionaire.this, NewSurveyCheckerService.class);
Log.d("hai","questionaire"+questionnaireId);
        stopService(intent);


        final ArrayList<Model> list= new ArrayList();
        final QuestionaireAdapter adapter = new QuestionaireAdapter(list,this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();

            }
        });

        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET,ipclass.url+"/api/getquestions/"+questionnaireId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONArray obj = new JSONArray();
                try {
                     obj = new JSONArray(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int i;
                for(i=0; i< obj.length(); i++)
                {
                     JSONObject x =new JSONObject();
                    try {
                         x = (JSONObject) obj.get(i);
                         final String question= (String) x.get("question_text");
                        final Integer idquestion= (Integer) x.get("idquestion");

                        mStringRequest2 = new StringRequest(Request.Method.GET, ipclass.url+"/api/getchoices/"+ x.get("idquestion"), new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                            final List<String> players = new ArrayList<String>();
                            final List<String> choice_ids = new ArrayList<>();
                            JSONArray obj2=new JSONArray();

                            try {
                                obj2 = new JSONArray(response.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            int z;
                            for(z=0;z<obj2.length();z++)
                            {
                                JSONObject y=new JSONObject();

                                try {
                                    y = (JSONObject) obj2.get(z);
                                    players.add(y.getString("choice"));
                                    choice_ids.add(y.getString("idchoices"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            list.add(new Model(Model.CHOICE_TYPE,question,R.drawable.add_icon,obj2.length(),players,idquestion,choice_ids));
                            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                            mRecyclerView.setLayoutManager(linearLayoutManager);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setAdapter(adapter);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Log.d("hai","Error :" + error.toString());
                            }
                        });
                        mRequestQueue.add(mStringRequest2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                   }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("hai","Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View tempView = view;
                int[] radioButtons = {R.id.radioButton,R.id.radioButton2,R.id.radioButton3,R.id.radioButton4,R.id.radioButton5,R.id.radioButton6};
                boolean uncheckedOption = false;
                for(int i=0;i<adapter.getItemCount();i++)
                {
                    int checkedRadioId = ((RadioGroup)mRecyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.RGroup)).getCheckedRadioButtonId();
                    if(checkedRadioId == -1)
                        uncheckedOption = true;
                }
                if(!uncheckedOption) {
                    for (int i = 0; i < adapter.getItemCount(); i++) {
                        int checkedRadioId = ((RadioGroup) mRecyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.RGroup)).getCheckedRadioButtonId();
                        JSONObject requestElement = new JSONObject();
                        try {
                            requestElement.put("qid", adapter.getQuestionId(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int j = 0; j < radioButtons.length; j++) {
                            if (checkedRadioId == radioButtons[j]) {
                                String choice_selected = adapter.dataSet.get(i).choices_id.get(j);
                                try {
                                    requestElement.put("choice", choice_selected);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mRequestQueue = Volley.newRequestQueue(view.getContext());
                                try {
                                    mStringRequest = new StringRequest(Request.Method.GET, ipclass.url + "/api/addresponse/" + requestElement.getString("qid") + "?response=" + requestElement.getString("choice") + "&userid=" + new PrefManager(view.getContext()).getuserId(), new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.d("CRESPOTER", "onResponse: " + response);
                                            Intent intent = new Intent(tempView.getContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                            Log.i("CRESPOTER", "Error :" + error.toString());
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mRequestQueue.add(mStringRequest);
                            }

                        }
                    }

                  //  Intent serviceIntent = new Intent(getBaseContext(), NewSurveyCheckerService.class);
                    //getBaseContext().startService(serviceIntent);
                }
                else
                {
                    Toast.makeText(Questionaire.this, "Please Fill the options", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;


        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;

        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Do nothing or catch the keys you want to block
        return false;
    }
}