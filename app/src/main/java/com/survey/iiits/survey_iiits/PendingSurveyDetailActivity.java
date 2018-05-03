/*
Author : [David Christie]
Last Edited : 4/27/2018
 */
package com.survey.iiits.survey_iiits;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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


public class PendingSurveyDetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest,mStringRequest2,requestForResponseCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createsurvey);
        Intent myIntent = getIntent();
        final String getdraftid = myIntent.getStringExtra("id");
        final ArrayList<PieModel> list= new ArrayList();
        final PendingSurveyDetailsAdapter adapter = new PendingSurveyDetailsAdapter(list,this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET,ipclass.url+"/api/getquestions/"+getdraftid, new Response.Listener<String>() {
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
                    JSONObject x;
                    try {
                        x = (JSONObject) obj.get(i);
                        final String question= (String) x.get("question_text");
                        final int questionId = (int) x.get("idquestion");
                        mStringRequest2 = new StringRequest(Request.Method.GET, ipclass.url+"/api/getcompletechoicedetails/"+ x.get("idquestion"), new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                final List<String> players = new ArrayList<String>(); ;
                                final List<String> choiceIds = new ArrayList<String>();
                                final List<String> responseCounts = new ArrayList<String>();
                                JSONArray obj2=new JSONArray();
                                try {
                                    obj2 = new JSONArray(response.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                int z;
                                for(z=0;z<obj2.length();z++)
                                {
                                    JSONObject y;

                                    try {
                                        y = (JSONObject) obj2.get(z);
                                        players.add(y.getString("choice"));
                                        choiceIds.add(y.getString("idchoices"));
                                        responseCounts.add(y.getString("responseChosen"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }


                                Log.d("IIITS", "onResponse: " + responseCounts.size());
                                list.add(new PieModel(Model.CHOICE_TYPE,question,R.drawable.add_icon,obj2.length(),players,questionId,choiceIds,responseCounts));
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
    }

}
