package com.example.mydailyexpenses;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import model.ExpensesDBModel;

public class CustomAdapterExpList extends RecyclerView.Adapter<CustomAdapterExpList.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(ExpensesDBModel item);
    }

    private final List<ExpensesDBModel> listExpenses;
    private final OnItemClickListener listener;

    public CustomAdapterExpList(List<ExpensesDBModel> expensesDBModels, OnItemClickListener listener) {
        this.listExpenses = expensesDBModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_expenses_recycler,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterExpList.ViewHolder holder, int position) {
        ExpensesDBModel expensesDBModel = holder.bind(listExpenses.get(position), listener);
        holder.txtVwExpName.setText(expensesDBModel.getStrExpName());
        holder.txtVwExpPrice.setText(String.valueOf(expensesDBModel.getStrExpPrice()));
        holder.txtVwExpDate.setText(expensesDBModel.getStrExpDate());
        holder.txtVwExpTime.setText(expensesDBModel.getStrExpTime());

    }

    @Override
    public int getItemCount() {
        return listExpenses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtVwExpName, txtVwExpPrice, txtVwExpDate, txtVwExpTime;
        public ViewHolder(View itemView){
            super(itemView);

            txtVwExpName = itemView.findViewById(R.id.txtVwExpName);
            txtVwExpPrice = itemView.findViewById(R.id.txtVwExpPrice);
            txtVwExpDate = itemView.findViewById(R.id.txtVwExpDate);
            txtVwExpTime = itemView.findViewById(R.id.txtVwTime);
        }

        public ExpensesDBModel bind(final ExpensesDBModel expensesDBModel, final OnItemClickListener listener) {
            txtVwExpName.setText(expensesDBModel.getStrExpName());
            txtVwExpPrice.setText(String.valueOf(expensesDBModel.getStrExpPrice()));
            txtVwExpDate.setText(expensesDBModel.getStrExpDate());
            txtVwExpTime.setText(expensesDBModel.getStrExpTime());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(expensesDBModel);
                }
            });
            return expensesDBModel;
        }
    }

}
