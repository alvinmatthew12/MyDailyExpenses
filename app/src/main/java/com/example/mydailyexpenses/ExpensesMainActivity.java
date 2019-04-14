package com.example.mydailyexpenses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.ExpensesDBModel;
import sqliteexpense.ExpensesDB;

public class ExpensesMainActivity extends AppCompatActivity {

    private final String strURL = "http://utem.web.id/globalWebService.php";

    EditText edtExpName,edtExpPrice,edtExpDate,edtExpTime;
    TextView txtVwExpId;
    Button btnSave;
    ProgressBar progressBar;

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_main);

        pref = getApplicationContext().getSharedPreferences("myDailyExpenses", 0);
        editor = pref.edit();

        txtVwExpId = findViewById(R.id.txtVwExpenseId);
        edtExpDate = findViewById(R.id.edtDate);
        edtExpName = findViewById(R.id.edtName);
        edtExpPrice = findViewById(R.id.edtPrice);
        edtExpTime = findViewById(R.id.edtTime);
        progressBar = findViewById(R.id.progressBar);
        btnSave = findViewById(R.id.btnSave);

        // Run if editMode is true
        if (pref.contains("editMode") && pref.getBoolean("editMode", false))
        {
            String id = pref.getString("expense_id", "");
            String name = pref.getString("expense_name", "");
            String date = pref.getString("expense_date", "");
            String time = pref.getString("expense_time", "");
            String price = pref.getString("expense_price", "");
            txtVwExpId.setText(id);
            edtExpName.setText(name);
            edtExpDate.setText(date);
            edtExpTime.setText(time);
            edtExpPrice.setText(price);
            btnSave.setText("Update");
            Toast.makeText(this, "You are editing expense with name: " + name, Toast.LENGTH_SHORT).show();

        } else {
        // Run if editMode is false
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>(){
                @Override
                public void onResponse(String response){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        edtExpDate.setText(jsonObject.getString("currDate"));
                        edtExpTime.setText(jsonObject.getString("currTime"));
                    } catch (Exception ee)
                    {
                        Log.e("onResponse", ee.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("ErrorListener",error.getMessage());
                }

            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("selectFn", "fnGetDateTime");

                    return params;
                }
            };
            requestQueue.add(stringRequest);
            btnSave.setText("Save");
        }
    }

    public void fnSave(final View view)
    {
        progressBar.setVisibility(View.VISIBLE);
        view.setClickable(false);


        final ExpensesDBModel expensesDBModel = new ExpensesDBModel(
                txtVwExpId.getText().toString(),
                edtExpName.getText().toString(),
                Double.parseDouble(edtExpPrice.getText().toString()),
                edtExpDate.getText().toString(),
                edtExpTime.getText().toString()
        );
        if (pref.getBoolean("editMode", false) == false)
        {
            ExpensesDB expensesDB = new ExpensesDB(getApplicationContext());
            expensesDB.fnInsertExpense(expensesDBModel);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        progressBar.setVisibility(View.INVISIBLE);
                        view.setClickable(true);
                    } catch (Exception ee)
                    {
                        Log.e("onResponse", ee.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.INVISIBLE);
                    view.setClickable(true);
                    Toast.makeText(getApplicationContext(),"Unable to make connection to web service", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError{
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("selectFn", "fnAddExpenses");
                    params.put("varExpName", expensesDBModel.getStrExpName());
                    params.put("varExpPrice", String.valueOf(expensesDBModel.getStrExpPrice()));
                    params.put("varMobileDate", expensesDBModel.getStrExpDate());
                    params.put("varMobileTime", expensesDBModel.getStrExpTime());
                    return params;

                }
            };
            requestQueue.add(stringRequest);
            Toast.makeText(getApplicationContext(), "Expenses Saved!", Toast.LENGTH_SHORT).show();
        } else {
            ExpensesDB expensesDB = new ExpensesDB(getApplicationContext());
            expensesDB.fnUpdateExpenses(expensesDBModel);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        progressBar.setVisibility(View.INVISIBLE);
                        view.setClickable(true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.INVISIBLE);
                    view.setClickable(true);
                    Toast.makeText(getApplicationContext(),"Unable to make connection to web service", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError{
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("updateFn", "fnUpdateExpenses");
                    params.put("varExpId", expensesDBModel.getStrExpId());
                    params.put("varExpName", expensesDBModel.getStrExpName());
                    params.put("varExpPrice", String.valueOf(expensesDBModel.getStrExpPrice()));
                    params.put("varMobileDate", expensesDBModel.getStrExpDate());
                    params.put("varMobileTime", expensesDBModel.getStrExpTime());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
            Toast.makeText(getApplicationContext(), "Expenses Updated!", Toast.LENGTH_SHORT).show();
            editor.putBoolean("editMode", false);
            editor.commit();
        }
        Intent intent = new Intent(this, ActivityExpList.class);
        startActivity(intent);
    }

    public void fnToActivity2(View view){
        Intent intent = new Intent(this, ActivityExpList.class);
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