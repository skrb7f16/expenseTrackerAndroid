package com.skrb7f16.expencetracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.skrb7f16.expencetracker.data.MyDbHandler;
import com.skrb7f16.expencetracker.model.ExpenseTracker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class MainActivity extends AppCompatActivity {
    int showAddButton=1;
    List<ExpenseTracker> expenseTrackerList=new ArrayList<>();
    Button button;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDbHandler db=new MyDbHandler(this);
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date=new Date();
        String dateString=formatter.format(date);
        dateString=dateString.split(" ")[0];
        expenseTrackerList=db.allExpence();
        checkToday(dateString);
        button=findViewById(R.id.addButton);
        if(showAddButton==0){
            button.setVisibility(View.INVISIBLE);
        }
        populate();
    }

    public void checkToday(String d){
        for (ExpenseTracker expenseTracker:expenseTrackerList){
            if(d.equals(expenseTracker.getDate())){
                showAddButton=0;
            }
        }

    }

    public void populate(){
        final ArrayList<String>expenses=new ArrayList<>();
        for(ExpenseTracker expenseTracker:expenseTrackerList){
            expenses.add(expenseTracker.getDate());
        }
        listView=findViewById(R.id.listOfDate);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,expenses);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,ExpensePage.class);
                intent.putExtra("id", ""+expenseTrackerList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    public void addNew(View view){
        Intent intent=new Intent(MainActivity.this,AddNew.class);
        startActivityForResult(intent,333);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==333){
                populate();
            }
    }
}