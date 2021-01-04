package com.skrb7f16.expencetracker.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseTracker {
    private int id;
    private String expense;
    private String reason;
    private String date;

    public ExpenseTracker(int id, String expense, String reason, String date) {
        this.id = id;
        this.expense = expense;
        this.reason = reason;
        this.date = date;
    }

    public ExpenseTracker() {
    }
    public ExpenseTracker(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Integer> getExpenceFromString(){
        List<Integer> expenceList=new ArrayList<>();
        String [] stringExpence=this.expense.split("\\+");
        Integer temp;
        for(int i=0;i<stringExpence.length;i++){
            temp=Integer.parseInt(stringExpence[i]);
            expenceList.add(temp);
        }
        return expenceList;
    }
    public List<String> getReasonFromString(){
        List<String> reasonList=new ArrayList<>();
        String [] reasonString=this.reason.split("\\+");
        String temp;
        for(int i=0;i<reasonString.length;i++){
            temp=reasonString[i];
            reasonList.add(temp);
        }
        return reasonList;
    }
}
