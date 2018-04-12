package com.survey.iiits.survey_iiits;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * Authors : [David Christie,Shyam Sunder]
 * Last Edited : 4/10/2018
 * Project : Pen Survey
 * Description : Survey application for IIIT Sricity
 */
public class QuestionaireAdapter extends RecyclerView.Adapter {


    public ArrayList<Model>dataSet;
    private ArrayList<ChoiceTypeViewHolder> setViewHolders = new ArrayList<>();
    public static class ChoiceTypeViewHolder extends RecyclerView.ViewHolder {
        TextView txtType;
        RadioButton[] r;
        public ChoiceTypeViewHolder(View itemView) {
            super(itemView);
            this.r=new RadioButton[6];
            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.r[0]=(RadioButton)itemView.findViewById(R.id.radioButton);
            this.r[1]=(RadioButton)itemView.findViewById(R.id.radioButton2);
            this.r[2]=(RadioButton)itemView.findViewById(R.id.radioButton3);
            this.r[3]=(RadioButton)itemView.findViewById(R.id.radioButton4);
            this.r[4]=(RadioButton)itemView.findViewById(R.id.radioButton5);
            this.r[5]=(RadioButton)itemView.findViewById(R.id.radioButton6);
        }
    }

    public QuestionaireAdapter(ArrayList<Model>data, Context context) {
        this.dataSet = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choicetypequestionaire, parent, false);
        return new ChoiceTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {
        Model object = dataSet.get(listPosition);
        setViewHolders.add(listPosition,(ChoiceTypeViewHolder)holder);
        if (object != null) {

            ((ChoiceTypeViewHolder) holder).txtType.setText(object.text);
            int num=object.choice_number;
            for (int i=num;i<6;i++) {
                ((ChoiceTypeViewHolder) holder).r[i].setVisibility(View.GONE);

            }
            for (int i=0;i<num;i++) {
                ((ChoiceTypeViewHolder) holder).r[i].setText(object.choices.get(i));
            }


        }
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    public int getQuestionId(int i)
    {
        return dataSet.get(i).qid;
    }


}