package com.survey.iiits.survey_iiits;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class My_Survey extends Fragment {
    RecyclerView recyclerView;
    // ArrayList for person names
    ArrayList personNames = new ArrayList<>(Arrays.asList("Shyam Sunder","David Christie"));
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.mine_icon, R.drawable.draft_icon, R.drawable.send_icon, R.drawable.mine_icon, R.drawable.menu_icon, R.drawable.home_icon, R.drawable.draft_icon));
    ArrayList topic = new ArrayList<>(Arrays.asList("Topic 1","Topic 2"));
    ArrayList note = new ArrayList<>(Arrays.asList("Note 1","Note 2"));
    ArrayList timeStamp = new ArrayList<>(Arrays.asList("2 hours ago","7/03/2018"));
    ArrayList questionId = new ArrayList<>(Arrays.asList("1","2"));
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myFragmentView =inflater.inflate(R.layout.my_surveys, container, false);

        // get the reference of RecyclerView
        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recyclerView_mysurvey);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // call the constructor of CustomAdapter to send the reference and data to Adapter

        HomeAdapter homeAdapter = new HomeAdapter(getActivity().getApplicationContext(), personNames, personImages,topic,note,timeStamp,questionId);
        recyclerView.setAdapter(homeAdapter); // set the Adapter to RecyclerView
        // implement setOnRefreshListener event on SwipeRefreshLayout



        return myFragmentView;

    }

}