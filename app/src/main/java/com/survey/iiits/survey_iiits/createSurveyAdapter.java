package com.survey.iiits.survey_iiits;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class createSurveyAdapter extends RecyclerView.Adapter<createSurveyAdapter.MyViewHolder> implements View.OnClickListener{
    ArrayList<Question> questions;
    Context context;
    ArrayList<String> writerquestions;
    RecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    public createSurveyAdapter(Context context) {
        this.context = context;
        questions = new ArrayList<Question>();
        writerquestions = new ArrayList<String>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.createsurveyformelement, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.d("hai", position + "asd" + writerquestions.get(position));
        holder.name.setText(writerquestions.get(position));
        //Context context = MyViewHolder.getContext();

    }

    @Override
    public int getItemCount() {

        return questions.size();
    }


    @Override
    public void onClick(View view) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.editText);



        }

        }


    }

