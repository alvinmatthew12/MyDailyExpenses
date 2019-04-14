package com.example.mydailyexpenses;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import model.ExpensesDBModel;

public class CustomAdapterExpList extends RecyclerView.Adapter<CustomAdapterExpList.ViewHolder>{
    private final List<ExpensesDBModel> listExpenses;
    private static RecyclerViewClickListener itemListener;

    public CustomAdapterExpList(List<ExpensesDBModel> expensesDBModels, RecyclerViewClickListener itemListener) {
        this.listExpenses = expensesDBModels;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_expenses_recycler,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterExpList.ViewHolder holder, int position) {
        ExpensesDBModel expensesDBModel = holder.bind(listExpenses.get(position));
        holder.txtVwExpName.setText(expensesDBModel.getStrExpName());
        holder.txtVwExpPrice.setText(String.valueOf(expensesDBModel.getStrExpPrice()));
        holder.txtVwExpDate.setText(expensesDBModel.getStrExpDate());
        holder.txtVwExpTime.setText(expensesDBModel.getStrExpTime());

    }

    @Override
    public int getItemCount() {
        return listExpenses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtVwExpName, txtVwExpPrice, txtVwExpDate, txtVwExpTime;
        public ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            txtVwExpName = itemView.findViewById(R.id.txtVwExpName);
            txtVwExpPrice = itemView.findViewById(R.id.txtVwExpPrice);
            txtVwExpDate = itemView.findViewById(R.id.txtVwExpDate);
            txtVwExpTime = itemView.findViewById(R.id.txtVwTime);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, getLayoutPosition());
        }

        public ExpensesDBModel bind(final ExpensesDBModel expensesDBModel) {
            txtVwExpName.setText(expensesDBModel.getStrExpName());
            txtVwExpPrice.setText(String.valueOf(expensesDBModel.getStrExpPrice()));
            txtVwExpDate.setText(expensesDBModel.getStrExpDate());
            txtVwExpTime.setText(expensesDBModel.getStrExpTime());
            return expensesDBModel;
        }
    }

}
