package com.survey.iiits.survey_iiits;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class Home extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ArrayList personNames = new ArrayList<>();
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.mine_icon, R.drawable.draft_icon, R.drawable.send_icon, R.drawable.mine_icon, R.drawable.menu_icon, R.drawable.home_icon, R.drawable.draft_icon));
    ArrayList topic = new ArrayList<>();
    ArrayList note = new ArrayList<>();
    ArrayList timeStamp = new ArrayList<>();
    ArrayList questionId = new ArrayList<>();
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myFragmentView =inflater.inflate(R.layout.home, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) myFragmentView.findViewById(R.id.simpleSwipeRefreshLayout);
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        mStringRequest = new StringRequest(Request.Method.GET, ipclass.url+"/api/getquestionnaire/foruser/" + (new PrefManager(container.getContext()).getuserId()), new Response.Listener<String>() { // TODO CHANGE HERE
            @Override
            public void onResponse(String response) {
                JSONArray obj = new JSONArray();
                try {
                    obj = new JSONArray(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int i;
                for(i=0; i< obj.length(); i++) {
                    JSONObject x = new JSONObject();
                    try {
                        x = (JSONObject) obj.get(i);
                        personNames.add(x.get("username"));
                        topic.add(x.get("title"));
                        note.add(x.get("note"));
                        timeStamp.add(x.get("timestamppost"));
                        questionId.add(x.get("idquestionnaire")+"");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recyclerView);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                HomeAdapter homeAdapter = new HomeAdapter(getActivity().getApplicationContext(), personNames, personImages,topic,note,timeStamp,questionId);
                recyclerView.setAdapter(homeAdapter); // set the Adapter to RecyclerView

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("HAI","Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                shuffleItems();
            }
        });



        return myFragmentView;

    }
    public void shuffleItems() {

    }
}