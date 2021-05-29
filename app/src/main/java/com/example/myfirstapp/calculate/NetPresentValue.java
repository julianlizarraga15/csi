package com.example.myfirstapp.calculate;

import com.example.myfirstapp.helper.NthRoot;

public class NetPresentValue {

    private double productValue;
    private double numOfPayments;
    private double annualInflation;
    private double netPresentValue;
    private double discount;
    private double saving;

    public NetPresentValue(double productValue, double numOfPayments, double annualInflation){

        this.productValue = productValue;
        this.numOfPayments = numOfPayments;
        this.annualInflation = annualInflation;
    }

    public void calculate(){

        double inflationMonthly;
        double this_payment;
        double monthlyPayment;
        double this_inflation;


        annualInflation = annualInflation+1;
        inflationMonthly = NthRoot.root(annualInflation, 12);

        this_payment = 1;

        monthlyPayment = productValue/numOfPayments;
        netPresentValue = 0;

        for (double i = 1; i <= numOfPayments; i=i+1) {
            this_inflation = Math.pow(inflationMonthly, i);
            this_payment = monthlyPayment/this_inflation;
            netPresentValue += this_payment;
        }


        discount = Math.round((100-netPresentValue*100/productValue)*100.0)/100.0;
        saving = productValue - netPresentValue;

        netPresentValue = Math.round(netPresentValue * 100.0) / 100.0;

    }

    public double getNetPresentValue(){
        return netPresentValue;
    }

    public double getDiscount(){
        return discount;
    }

    public double getSaving(){
        return saving;
    }
}
