package com.survey.iiits.survey_iiits;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

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


public class CreateSurveyActivity extends AppCompatActivity {
    createSurveyAdapter adapter;
    RecyclerView recyclerView;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    ArrayList<Model> list2;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest,mStringRequest2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createsurvey);
        Intent myIntent = getIntent(); // gets the previously created intent
        String getdraftid = myIntent.getStringExtra("draftid"); // will return "FirstKeyValue"
Log.d("hai",getdraftid+"id");

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
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CreateSurveyActivity.this);

                alert.setTitle("Questionair");
                alert.setMessage("Question Need to be asked?");

// Set an EditText view to get user input
                final EditText input = new EditText(CreateSurveyActivity.this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        // Do something with value!
                        adapter.addQuestion(input.getText());
                        //adapter.questions.get(adapter.getItemCount()-1).question_details.
                        recyclerView.setAdapter(adapter);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                //alertDialog.setIcon(R.drawable.icon);
                alert.show();

            }
        });


        final ArrayList<Model> list= new ArrayList();
        final QuestionaireAdapter adapter = new QuestionaireAdapter(list,this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);


        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET,ipclass.url+"/api/getquestions/"+getdraftid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                Log.d("hai","resp"+response.toString());
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
                        if(x.get("type").equals("CHOICE"))
                        {
                            //         sendAndRequestResponse();
                            //       Log.d("hai","emter");
                            mStringRequest2 = new StringRequest(Request.Method.GET, ipclass.url+"/api/getchoices/"+ x.get("idquestion"), new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    final List<String> players = new ArrayList<String>(); ;
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
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

//Log.d("hai",)
                                    //                      players.add("hai");
                                    //                    players.add("bye");
                                    Log.d("hai",response.toString()+"resp2");
                                    list.add(new Model(Model.CHOICE_TYPE,question,R.drawable.add_icon,obj2.length(),players,1,null));


                                    // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                                    RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.createSurvey_recyclerView);
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
                        }





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



        Log.d("hai",list2+"list");



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
}