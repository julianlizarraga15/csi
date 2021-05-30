package com.example.myfirstapp.helper;

public class EstimatedInflationAtDate {

    private String date;
    private float value;

    public EstimatedInflationAtDate(String date, float value) {
        this.date = date;
        this.value = value;
    }

    public EstimatedInflationAtDate(){

    }
    public String getDate() {
        return date;
    }

    public void setD(String date) {
        this.date = date;
    }

    public String getValue() {
        return String.valueOf(value);
    }

    public void setV(float value) {
        this.value = value;
    }

    @Override
    public String toString(){
       StringBuffer sb = new StringBuffer();
       sb.append("Date: "+this.date);
       sb.append("Value: "+this.value);

       return sb.toString();
    }

}
