package com.survey.iiits.survey_iiits;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
public class createSurveyAdapter extends RecyclerView.Adapter<createSurveyAdapter.MyViewHolder> {
    ArrayList<Question> questions;
    Context context;

    public void addQuestion()
    {
        questions.add(new Question());
    }

    public createSurveyAdapter(Context context) {
        this.context = context;
        questions = new ArrayList<Question>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.createsurveyformelement, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }




    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {

        return questions.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
