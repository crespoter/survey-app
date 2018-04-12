package com.survey.iiits.survey_iiits;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements View.OnClickListener {
    ArrayList personNames;
    ArrayList personImages;
    ArrayList topic;
    ArrayList note;
    ArrayList timeStamp;
    ArrayList questionId;
    Context context;
    public HomeAdapter(Context context, ArrayList personNames, ArrayList personImages,ArrayList topic,ArrayList note,ArrayList timeStamp,ArrayList questionID) {
        this.context = context;
        this.personNames = personNames;
        this.personImages = personImages;
        this.questionId=questionID;
        this.topic=topic;
        this.note=note;
        this.timeStamp=timeStamp;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // set the data in items
        holder.name.setText((CharSequence) personNames.get(position));
        holder.image.setImageResource((Integer) personImages.get(position));
        holder.sub.setText((CharSequence)topic.get(position));
        holder.note.setText((CharSequence)note.get(position));
        holder.timestamp.setText((CharSequence)timeStamp.get(position));


        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
             //   Toast.makeText(context,holder.name.getText(), Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(context, Questionaire.class);
                myIntent.putExtra("firstKeyName",(CharSequence)questionId.get(position));
                myIntent.putExtra("secondKeyName","SecondKeyValue");
                context.startActivity(myIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return personNames.size();
    }

    @Override
    public void onClick(View view) {
    //    Toast.makeText(view.getContext(), "position = " +this.getItemCount(), Toast.LENGTH_SHORT).show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name,sub,note,timestamp;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            sub = (TextView) itemView.findViewById(R.id.sub);
            note = (TextView) itemView.findViewById(R.id.note);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);



            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
