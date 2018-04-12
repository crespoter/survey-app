package com.survey.iiits.survey_iiits;

import java.util.List;

public class Model {

    public static final int TEXT_TYPE=0;
    public static final int CHOICE_TYPE=1;

    public int type;
    public int data;
    public int qid;

    public int choice_number;
    public String text;
    public List<String>  choices;
    public List<String> choices_id;
    public Model(int type, String text, int data, int choice_number,List<String>  choices,int qid,List<String> choices_id)
    {this.qid=qid;
        this.type=type;
        this.data=data;
        this.text=text;
        this.choice_number=choice_number;
        this.choices=choices;
        this.choices_id = choices_id;
    }
}