package com.survey.iiits.survey_iiits;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList personNames;
    ArrayList personImages;
    ArrayList functions;
    Context context;

    public CustomAdapter(Context context, ArrayList personNames, ArrayList personImages) {
        this.context = context;
        this.personNames = personNames;
        this.personImages = personImages;
        this.functions = null;
    }

    public CustomAdapter(Context context, ArrayList personNames, ArrayList personImages,ArrayList functions) {
        this.context = context;
        this.personNames = personNames;
        this.personImages = personImages;
        this.functions = functions;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
// infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
// set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
// set the data in items
        Log.d("hai", (String) personNames.get(position));
        holder.name.setText((CharSequence) personNames.get(position));
        holder.image.setImageResource((Integer) personImages.get(position));
// implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// open another activity on item click
                Toast.makeText(context, (CharSequence) personNames.get(position),
                        Toast.LENGTH_SHORT).show();
                if(functions != null)
                {
                    Log.d("CRESPOTER", "onClick: LOADING INTENT");
                    Intent intent = new Intent(context,CreateSurveyActivity.class);
                    context.startActivity(intent);
                }
                // Intent intent = new Intent(context, SecondActivity.class);
               // intent.putExtra("image", (Integer) personImages.get(position)); // put image data in Intent
               // context.startActivity(intent); // start Intent
            }
        });

    }

    @Override
    public int getItemCount() {
        return personNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

// get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);

        }
    }
}