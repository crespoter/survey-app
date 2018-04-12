package com.survey.iiits.survey_iiits;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;




public class MakeDraft extends Fragment {

    // ArrayList for person names
    ArrayList personNames = new ArrayList<>(Arrays.asList("Topic 1", "Topic 2", "Topic 3", "Topic 4", "Topic 5", "Topic 6", "Topic 7"));
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.home_icon, R.drawable.draft_icon, R.drawable.menu_icon, R.drawable.send_icon, R.drawable.menu_icon, R.drawable.mine_icon, R.drawable.send_icon));


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.drafts, container, false);
// get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recyclerView);
// set a LinearLayoutManager with default vertical orientaion
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
// call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(getActivity().getApplicationContext(), personNames,personImages);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        return myFragmentView;
    }
}