package com.skrb7f16.expencetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.skrb7f16.expencetracker.data.MyDbHandler;
import com.skrb7f16.expencetracker.model.ExpenseTracker;

import java.util.ArrayList;
import java.util.List;

public class ExpensePage extends AppCompatActivity {
    int id;
    List<Integer> expenses;
    List<String> reasons;
    ExpenseTracker expenseTracker;
    MyDbHandler db;
    int total=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_page);
        Intent intent=getIntent();
        id=Integer.parseInt(intent.getStringExtra("id"));
        db=new MyDbHandler(ExpensePage.this);
        expenseTracker=db.getExpenceObject(id);
        TextView textView=findViewById(R.id.date);
        textView.setText(expenseTracker.getDate());
        populate();
    }

    public void populate(){
        expenses=expenseTracker.getExpenceFromString();
        reasons=expenseTracker.getReasonFromString();
        final ArrayList<String> oneDayExpenses=new ArrayList<>();
        for (int i=0;i<reasons.size();i++){
            oneDayExpenses.add("Expense : "+expenses.get(i)+"\nReason : "+reasons.get(i));
            total+=expenses.get(i);
        }
        ListView listView=findViewById(R.id.expenselistView);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,oneDayExpenses);
        listView.setAdapter(arrayAdapter);
        TextView textView=findViewById(R.id.total);
        textView.setText("Total : "+total);
        
    }

    public void updateExpenseByUser(View view){
        String tempAmount,tempReason,temp;
        EditText editText=findViewById(R.id.amountText);
        tempAmount=editText.getText().toString();
        editText.setText("");
        editText=findViewById(R.id.reasonText);
        tempReason=editText.getText().toString();
        editText.setText("");
        if(tempAmount.length()==0||tempReason.length()==0){
            Toast.makeText(this,"Amount and reason can't be empty",Toast.LENGTH_SHORT);
            return;
        }
        temp=expenseTracker.getExpense();
        temp+="+"+tempAmount;
        expenseTracker.setExpense(temp);
        temp=expenseTracker.getReason();
        temp+="+"+tempReason;
        expenseTracker.setReason(temp);
        int t=db.UpdateExpenceOnOneDay(expenseTracker);
        if(t==1){
            Toast.makeText(this,"Expense added successfully",Toast.LENGTH_SHORT);
            populate();
        }
        else{
            Toast.makeText(this,"Error while adding",Toast.LENGTH_SHORT);
        }
    }
}