package com.survey.iiits.survey_iiits;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Home extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    // ArrayList for person names
    ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7"));
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.mine_icon, R.drawable.draft_icon, R.drawable.send_icon, R.drawable.mine_icon, R.drawable.menu_icon, R.drawable.home_icon, R.drawable.draft_icon));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myFragmentView =inflater.inflate(R.layout.home, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) myFragmentView.findViewById(R.id.simpleSwipeRefreshLayout);
        // get the reference of RecyclerView
        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // call the constructor of CustomAdapter to send the reference and data to Adapter
        HomeAdapter homeAdapter = new HomeAdapter(getActivity().getApplicationContext(), personNames, personImages);
        recyclerView.setAdapter(homeAdapter); // set the Adapter to RecyclerView
        // implement setOnRefreshListener event on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh
                swipeRefreshLayout.setRefreshing(false);
                shuffleItems();
            }
        });



        return myFragmentView;

    }
    public void shuffleItems() {
        // shuffle the ArrayList's items and set the adapter
        Collections.shuffle(personNames, new Random(System.currentTimeMillis()));
        Collections.shuffle(personImages, new Random(System.currentTimeMillis()));
        // call the constructor of CustomAdapter to send the reference and data to Adapter
        HomeAdapter homeAdapter = new HomeAdapter(getActivity().getApplicationContext(), personNames, personImages);
        recyclerView.setAdapter(homeAdapter); // set the Adapter to RecyclerView
    }
}