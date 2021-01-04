package com.skrb7f16.expencetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.skrb7f16.expencetracker.data.MyDbHandler;
import com.skrb7f16.expencetracker.model.ExpenseTracker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNew extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
    }

    public void addNewDay(View view){
        String tempAmount,tempReason;
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date=new Date();
        String dateString=formatter.format(date);
        dateString=dateString.split(" ")[0];
        ExpenseTracker expenseTracker=new ExpenseTracker();
        expenseTracker.setDate(dateString);
        EditText editText=findViewById(R.id.amountNewText);
        tempAmount=editText.getText().toString();
        editText=findViewById(R.id.reasonNewText);
        tempReason=editText.getText().toString();
        if(tempAmount.length()==0||tempReason.length()==0){
            Toast.makeText(this,"Cannot be empty",Toast.LENGTH_SHORT);
            return;
        }
        expenseTracker.setExpense(tempAmount);
        expenseTracker.setReason(tempReason);
        MyDbHandler db=new MyDbHandler(this);
        db.addExpence(expenseTracker);
        finishActivity(333);
    }
}