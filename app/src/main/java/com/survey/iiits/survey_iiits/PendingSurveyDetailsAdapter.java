package com.survey.iiits.survey_iiits;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/*
 * Authors : [David Christie,Shyam Sunder]
 * Last Edited : 4/10/2018
 * Project : Pen Survey
 * Description : Survey application for IIIT Sricity
 */
public class PendingSurveyDetailsAdapter extends RecyclerView.Adapter {


    public ArrayList<PieModel>dataSet;
    private boolean editableText;
    private ArrayList<ChoiceTypeViewHolder> setViewHolders = new ArrayList<>();
    public static class ChoiceTypeViewHolder extends RecyclerView.ViewHolder {
        TextView txtType;
        RadioButton[] r;
        PieChart pieChart;
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
            pieChart = (PieChart) itemView.findViewById(R.id.chart);
        }
    }
    public PendingSurveyDetailsAdapter(ArrayList<PieModel>data,Context context)
    {
        this.dataSet = data;
        this.editableText = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.surveydetails, parent, false);
        return new ChoiceTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {
        PieModel object = dataSet.get(listPosition);
        setViewHolders.add(listPosition,(ChoiceTypeViewHolder)holder);

        if (object != null) {



            ((ChoiceTypeViewHolder) holder).txtType.setText(object.text);
            int num=object.choice_number;
            for (int i=num;i<6;i++) {

                ((ChoiceTypeViewHolder) holder).r[i].setVisibility(View.GONE);

            }
            for (int i=0;i<num;i++) {
                ((ChoiceTypeViewHolder) holder).r[i].setText(object.choices.get(i));
                ((ChoiceTypeViewHolder) holder).r[i].setEnabled(editableText);
            }

            List<PieEntry> entries = new ArrayList<PieEntry>();
            for(int i=0;i<dataSet.get(listPosition).response_count.size();i++)
            {
                Log.d("IIITS", "onBindViewHolder: Added value to graph" + Integer.parseInt(dataSet.get(listPosition).response_count.get(i)) );
                entries.add(new PieEntry(Integer.parseInt(dataSet.get(listPosition).response_count.get(i)),i));
                entries.get(i).setLabel("Choice " + (i+1));
            }
            PieDataSet data = new PieDataSet(entries,"Poll Result");
            data.setColors(ColorTemplate.VORDIPLOM_COLORS);
            PieData piedata = new PieData(data);
            ((ChoiceTypeViewHolder) holder).pieChart.setData(piedata);
            ((ChoiceTypeViewHolder) holder).pieChart.invalidate();
        }
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}