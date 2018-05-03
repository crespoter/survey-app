/*
    Authors : [David Christie,]
    Last Edited : 4/22/2018
 */
package com.survey.iiits.survey_iiits;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Arrays;


public class Pending_Survey extends Fragment {
    ArrayList personNames = new ArrayList<>(Arrays.asList());

    ArrayList questionnaireId = new ArrayList<>(Arrays.asList());
    ArrayList functions = new ArrayList<>();
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    public Pending_Survey() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myFragmentView = inflater.inflate(R.layout.drafts, container, false);
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        mStringRequest = new StringRequest(Request.Method.GET, ipclass.url+"/api/getquestionnaire/createdByUser/running/"+(new PrefManager(container.getContext()).getuserId()), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
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
                        personNames.add(y.getString("title"));
                        questionnaireId.add(y.get("idquestionnaire"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                RecyclerView recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recyclerView);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                pendingSurveyAdapter customAdapter = new pendingSurveyAdapter(getActivity().getApplicationContext(), personNames,functions,questionnaireId);
                recyclerView.setAdapter(customAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("IIITS","Error :" + error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
        return myFragmentView;
    }
}