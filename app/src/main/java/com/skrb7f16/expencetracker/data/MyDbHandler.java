package com.skrb7f16.expencetracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.skrb7f16.expencetracker.Params.Params;
import com.skrb7f16.expencetracker.model.ExpenseTracker;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {

    public  MyDbHandler(Context context){
        super(context, Params.DB_NAME,null,Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="create table "+Params.TABLE_NAME+"("
                +Params.KEY_ID+" integer primary key, "+Params.KEY_EXPENSE+" text, "+Params.KEY_REASON
                +" text, "+ Params.KEY_DATE+" text "+')';
        Log.d("skrb7f16db","hello"+create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addExpence(ExpenseTracker expenseTracker){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Params.KEY_EXPENSE,expenseTracker.getExpense());
        contentValues.put(Params.KEY_REASON,expenseTracker.getReason());
        contentValues.put(Params.KEY_DATE,expenseTracker.getDate());
        db.insert(Params.TABLE_NAME,null,contentValues);
        db.close();
        Log.d("meow","added successfully");
    }

    public List<ExpenseTracker> allExpence(){
        List<ExpenseTracker> expenseTrackerList=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String select="SELECT * FROM "+Params.TABLE_NAME;
        Cursor cursor=db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do {
                ExpenseTracker expenseTracker=new ExpenseTracker();
                expenseTracker.setId(Integer.parseInt(cursor.getString(0)));
                expenseTracker.setExpense(cursor.getString(1));
                expenseTracker.setReason(cursor.getString(2));
                expenseTracker.setDate(cursor.getString(3));
                expenseTrackerList.add(expenseTracker);
            }while(cursor.moveToNext());
        }
        db.close();
        return expenseTrackerList;
    }

    public int UpdateExpenceOnOneDay(ExpenseTracker expenseTracker){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Params.KEY_EXPENSE,expenseTracker.getExpense());
        contentValues.put(Params.KEY_REASON,expenseTracker.getReason());
        contentValues.put(Params.KEY_DATE,expenseTracker.getDate());
        return db.update(Params.TABLE_NAME,contentValues,Params.KEY_ID+"=?",new String[]{String.valueOf(expenseTracker.getId())});
    }


    public ExpenseTracker getExpenceObject(int id){
        ExpenseTracker expenseTracker=new ExpenseTracker();
        SQLiteDatabase db=getReadableDatabase();
        String select = "select * from "+Params.TABLE_NAME+" where "+Params.KEY_ID+" = "+id;
        Cursor cursor=db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            expenseTracker.setId(Integer.parseInt(cursor.getString(0)));
            expenseTracker.setExpense(cursor.getString(1));
            expenseTracker.setReason(cursor.getString(2));
            expenseTracker.setDate(cursor.getString(3));
        }
        return expenseTracker;
    }

    public void setExpenceObject(ExpenseTracker expenseTracker){
        SQLiteDatabase db=getReadableDatabase();
        String select = "select * from "+Params.TABLE_NAME+" where "+Params.KEY_ID+" = "+expenseTracker.getId();
        Cursor cursor=db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            expenseTracker.setId(Integer.parseInt(cursor.getString(0)));
            expenseTracker.setExpense(cursor.getString(1));
            expenseTracker.setReason(cursor.getString(2));
            expenseTracker.setDate(cursor.getString(3));
        }
    }
}
