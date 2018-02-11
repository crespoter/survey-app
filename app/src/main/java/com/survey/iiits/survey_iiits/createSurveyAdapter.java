package com.survey.iiits.survey_iiits;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
public class createSurveyAdapter extends RecyclerView.Adapter<createSurveyAdapter.MyViewHolder> {
    ArrayList<Question> questions;
    Context context;
    ArrayList<String> writerquestions;

    public void addQuestion(Editable text)
    {
        questions.add(new Question());writerquestions.add(new String(String.valueOf(text)));
    }

    public createSurveyAdapter(Context context) {
        this.context = context;
        questions = new ArrayList<Question>();
        writerquestions=new ArrayList<String>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.createsurveyformelement, parent, false);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }




    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.d("hai",position+"asd"+writerquestions.get(position));
holder.name.setText(writerquestions.get(position));
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
            name = (TextView) itemView.findViewById(R.id.createsurveyelement_question);

           image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
