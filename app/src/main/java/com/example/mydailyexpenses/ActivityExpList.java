package com.example.mydailyexpenses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import model.ExpensesDBModel;
import sqliteexpense.ExpensesDB;

public class ActivityExpList extends AppCompatActivity {

    RecyclerView recyclerViewExpList;
    ArrayList<ExpensesDBModel> expensesList;

    ExpensesDB expensesDB;
    CustomAdapterExpList customAdapterExpList;

    TextView txtVwSum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_list);

        recyclerViewExpList = findViewById(R.id.recyListExp);

        expensesDB = new ExpensesDB(getApplicationContext());
        expensesList = (ArrayList<ExpensesDBModel>) expensesDB.fnGetAllExpenses();
        customAdapterExpList = new CustomAdapterExpList(expensesDB.fnGetAllExpenses());

        recyclerViewExpList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExpList.setAdapter(customAdapterExpList);
        customAdapterExpList.notifyDataSetChanged();

//        sum expenses call value from ExpensesDB function
        txtVwSum = findViewById(R.id.txtVwSum);
        txtVwSum.setText(String.valueOf(expensesDB.fnGetTotalExpenses()));

    }


}
