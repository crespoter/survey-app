/*
    Authors : [David Christie,Shyam Sunder]
    Date : 4/12/2018
 */
package com.survey.iiits.survey_iiits;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.RadioButton;
import android.widget.RadioGroup;
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


    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest,mStringRequest2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createsurvey);
        Intent myIntent = getIntent();
        final String getdraftid = myIntent.getStringExtra("draftid");
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
                final String draftId = getdraftid;
               // Intent viewGroups = new Intent(view.getContext(),SelectGroup.class);
                //viewGroups.putExtra("draft_id",draftId);
                //startActivity(viewGroups);
                final CharSequence[] items = {"Anonymous"," Force"};
// arraylist to keep the selected items
                final ArrayList seletedItems=new ArrayList();
                AlertDialog dialog = new AlertDialog.Builder(CreateSurveyActivity.this)
                        .setTitle("Select respnse type(only official use)")
                        .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    seletedItems.add(indexSelected);
                                } else if (seletedItems.contains(indexSelected)) {
                                    // Else, if the item is already in the array, remove it
                                    seletedItems.remove(Integer.valueOf(indexSelected));
                                }
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on OK
                                //  You can write the code  to save the selected item here
                               // String draft_id="0";
                                String anonymous="0";
                                String forcedResponse="0";
                                if(seletedItems.contains(1)==true)
                                {
                                 forcedResponse="1";
                                }
                                if(seletedItems.contains(0)==true)
                                {
                                    anonymous="1";
                                }

                                Intent viewGroups = new Intent(CreateSurveyActivity.this,SelectGroup.class);

                                viewGroups.putExtra("draft_id",draftId);
                                viewGroups.putExtra("anonymous",anonymous);
                                viewGroups.putExtra("forcedResponse",forcedResponse);
                                startActivity(viewGroups);

                                Log.d("hai",seletedItems.toString());
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on Cancel
                            }
                        }).create();
                dialog.show();
              //  Intent setPreference = new Intent(view.getContext(),surveyPreference.class);
              //  setPreference.putExtra("draft_id",draftId);
                //startActivity(setPreference);
               // finish();
            }
        });


        final ArrayList<Model> list= new ArrayList();
        final QuestionaireAdapter adapter = new QuestionaireAdapter(list,this,false);
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
                                    JSONObject y;

                                    try {
                                        y = (JSONObject) obj2.get(z);
                                        players.add(y.getString("choice"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                list.add(new Model(Model.CHOICE_TYPE,question,R.drawable.add_icon,obj2.length(),players,1,null));
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