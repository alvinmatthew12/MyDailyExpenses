package com.example.mydailyexpenses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import model.ExpensesDBModel;
import sqliteexpense.ExpensesDB;

public class ExpensesMainActivity extends AppCompatActivity {

    EditText edtExpName,edtExpPrice,edtExpDate;
    String strURL = "http://localhost/webServiceJSON/globalWebService.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_main);

        edtExpDate = findViewById(R.id.edtDate);
        edtExpName = findViewById(R.id.edtName);
        edtExpPrice = findViewById(R.id.edtPrice);

    }

    public void fnSave(View view)
    {
        ExpensesDBModel expensesDBModel = new ExpensesDBModel(edtExpName.getText().toString(), Double.parseDouble(edtExpPrice.getText().toString()),edtExpDate.getText().toString());
        ExpensesDB expensesDB = new ExpensesDB(getApplicationContext());
        expensesDB.fnInsertExpense(expensesDBModel);

        Toast.makeText(getApplicationContext(), "Expenses Saved!", Toast.LENGTH_SHORT).show();
    }

    public void fnToActivity2(View view){
        Intent intent = new Intent(this, ActivityExpList.class);
        startActivity(intent);
    }

    


}
