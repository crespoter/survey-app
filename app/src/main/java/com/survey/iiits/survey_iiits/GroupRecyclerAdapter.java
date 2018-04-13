package com.survey.iiits.survey_iiits;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

/**
 * Created by David Christie on 4/13/2018.
 */

public class GroupRecyclerAdapter extends RecyclerView.Adapter{
    private ArrayList<String> groupNames,groupIds;
    private String draftid;
    private Context context;
    public GroupRecyclerAdapter(Context context, ArrayList<String> groupNames,ArrayList<String> groupIds,String draftid) {
        this.context = context;
        this.groupNames = groupNames;
        this.groupIds = groupIds;
        this.draftid = draftid;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_group_element, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((TextView)((MyViewHolder)holder).name).setText(groupNames.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue mRequestQueue = Volley.newRequestQueue(context);
                StringRequest mStringRequest = new StringRequest(Request.Method.GET, ipclass.url+"/api/sendquestionnaire/"+groupIds.get(position)+"?id="+draftid, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Intent mainPage = new Intent(context,MainActivity.class);
                        context.startActivity(mainPage);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("hai","Error :" + error.toString());
                    }
                });
                mRequestQueue.add(mStringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupNames.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.select_group_element_groupname);
        }
    }
}
