package com.example.mydailyexpenses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

import model.ExpensesDBModel;
import sqliteexpense.ExpensesDB;

public class ActivityExpList extends AppCompatActivity implements RecyclerViewClickListener{


    RecyclerView recyclerViewExpList;
    CustomAdapterExpList customAdapterExpList;

    ExpensesDB expensesDB;

    ArrayList<ExpensesDBModel> expensesList;
    TextView txtVwSum;

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_list);

        recyclerViewExpList = findViewById(R.id.recyListExp);

        expensesDB = new ExpensesDB(getApplicationContext());
        expensesList = (ArrayList<ExpensesDBModel>) expensesDB.fnGetAllExpenses();
        customAdapterExpList = new CustomAdapterExpList(expensesDB.fnGetAllExpenses(), this);

        recyclerViewExpList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExpList.setAdapter(customAdapterExpList);
        customAdapterExpList.notifyDataSetChanged();

        txtVwSum = findViewById(R.id.txtVwSum);
        txtVwSum.setText(String.valueOf(expensesDB.fnGetTotalExpenses()));

        pref = getApplicationContext().getSharedPreferences("myDailyExpenses", 0);
        editor = pref.edit();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        ExpensesDBModel clickedItem = expensesList.get(position);

        editor.putBoolean("editMode", true);
        editor.putString("expense_id", clickedItem.getStrExpId());
        editor.putString("expense_name", clickedItem.getStrExpName());
        editor.putString("expense_date", clickedItem.getStrExpDate());
        editor.putString("expense_time", clickedItem.getStrExpTime());
        editor.putString("expense_price", String.valueOf(clickedItem.getStrExpPrice()));
        editor.commit();

        Intent intent = new Intent(this, ExpensesMainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int idMenu = item.getItemId();
        Intent intent = null;
        if (idMenu == R.id.addNewExpense)
        {
            editor.putBoolean("editMode", false);
            editor.commit();

            intent = new Intent(this, ExpensesMainActivity.class);
        }
        else if (idMenu == R.id.listExpenses)
        {
            intent = new Intent(this, ActivityExpList.class);
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
