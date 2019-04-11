package com.example.mydailyexpenses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.ExpensesDBModel;
import sqliteexpense.ExpensesDB;

public class ExpensesMainActivity extends AppCompatActivity {

    EditText edtExpName,edtExpPrice,edtExpDate,edtExpTime;
    String strURL = "http://utem.web.id/globalWebService.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_main);

        edtExpDate = findViewById(R.id.edtDate);
        edtExpName = findViewById(R.id.edtName);
        edtExpPrice = findViewById(R.id.edtPrice);
        edtExpTime = findViewById(R.id.edtTime);

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

    }

    public void fnSave(View view)
    {
        ExpensesDBModel expensesDBModel = new ExpensesDBModel(edtExpName.getText().toString(), Double.parseDouble(edtExpPrice.getText().toString()),edtExpDate.getText().toString(), edtExpTime.getText().toString());
        ExpensesDB expensesDB = new ExpensesDB(getApplicationContext());
        expensesDB.fnInsertExpense(expensesDBModel);

        Toast.makeText(getApplicationContext(), "Expenses Saved!", Toast.LENGTH_SHORT).show();
    }

    public void fnToActivity2(View view){
        Intent intent = new Intent(this, ActivityExpList.class);
        startActivity(intent);
    }

    


}
