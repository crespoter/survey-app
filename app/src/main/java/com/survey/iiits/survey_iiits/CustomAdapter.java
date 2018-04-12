/*
    Authors : [David Christie,Shyam Sunder]
    Last Edited : 4/12/2018
 */
package com.survey.iiits.survey_iiits;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList personNames;
    ArrayList personImages;
    ArrayList functions;
    ArrayList draftid;

    Context context;

    public CustomAdapter(Context context, ArrayList personNames, ArrayList personImages) {
        this.context = context;
        this.personNames = personNames;
        this.personImages = personImages;
        this.functions = null;
        this.draftid=null;
    }

    public CustomAdapter(Context context, ArrayList personNames,ArrayList functions,ArrayList draftid) {
        this.context = context;
        this.personNames = personNames;
        this.functions = functions;
        this.draftid=draftid;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.d("hai", (String) personNames.get(position));
        holder.name.setText((CharSequence) personNames.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(functions != null)
                {
                    Intent intent = new Intent(context,CreateSurveyActivity.class);
                    intent.putExtra("draftid",draftid.get(position)+"");
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return personNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}