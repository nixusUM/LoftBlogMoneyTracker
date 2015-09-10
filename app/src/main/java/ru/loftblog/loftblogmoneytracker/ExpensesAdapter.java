package ru.loftblog.loftblogmoneytracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.CardViewHolder> {

    List<Expense> expenses;

    public ExpensesAdapter(List<Expense> expenses){
        this.expenses = expenses;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false );
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.name.setText(expense.getTitle());
        holder.sum.setText(String.valueOf(expense.getSum()));
        holder.date.setText(expense.getTime());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }
    public class CardViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView sum;
        protected TextView date;
        public CardViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_text);
            sum = (TextView) itemView.findViewById(R.id.sum_text);
            date = (TextView) itemView.findViewById(R.id.date_text);
        }
    }
    }

