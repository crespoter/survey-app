package com.survey.iiits.survey_iiits;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SendSurvey extends Fragment {

    // ArrayList for person names
    ArrayList personNames = new ArrayList<>(Arrays.asList());

    ArrayList draftid = new ArrayList<>(Arrays.asList());
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.draft_icon));
    ArrayList functions = new ArrayList<>();
    private RequestQueue mRequestQueue,mRequestQueue2;
    private StringRequest mStringRequest,mStringRequest2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myFragmentView = inflater.inflate(R.layout.drafts, container, false);
// get the reference of RecyclerView
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        mStringRequest = new StringRequest(Request.Method.GET, ipclass.url+"/api/getdrafts/1", new Response.Listener<String>() {

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
                        personNames.add(y.getString("title"));
                        draftid.add(y.get("iddrafts"));
                //        players.add(y.getString("choice"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                RecyclerView recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recyclerView);
// set a LinearLayoutManager with default vertical orientaion
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter


                Log.d("hai",personNames+"names");
                CustomAdapter customAdapter = new CustomAdapter(getActivity().getApplicationContext(), personNames,personImages,functions,draftid);
                recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView


//Log.d("hai",)
                //                      players.add("hai");
                //                    players.add("bye");
                Log.d("hai",response.toString()+"resp3");
              //  list.add(new Model(Model.CHOICE_TYPE,question,R.drawable.add_icon,obj2.length(),players));


                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("hai","Error :" + error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);







        return myFragmentView;
    }
}