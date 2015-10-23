package ru.loftblog.loftblogmoneytracker.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.database.models.Expenses;

public class ExpensesAdapter extends SelectableAdapter<ExpensesAdapter.CardViewHolder> {

    private List<Expenses> expenses;
    private CardViewHolder.ClickListener clickListener;
    private Context context;
    private int lastPosition = -1;

    public ExpensesAdapter(List<Expenses> expenses, CardViewHolder.ClickListener clickListener) {
        this.expenses = expenses;
        this.clickListener = clickListener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        context = parent.getContext();
        return new CardViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Expenses expense = expenses.get(position);
        holder.name.setText(expense.getName());
        holder.sum.setText(String.valueOf(expense.getPrice()));
        holder.category.setText(expense.categories.toString());
        holder.date.setText(expense.getDate());
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
        setAnimation(holder.cardView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void removeItem(int position) {
        removeExpenses(position);
        notifyItemRemoved(position);
    }

    public void removeItems(List<Integer> positions) {
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                removeItem(positions.get(0));
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count ) {
                    count++;
                }
                removeRange(positions.get(count - 1), count);
                for (int i = 0; i < count; i++) {
                    positions.remove(0);
                }
            }
        }
    }

    private void removeExpenses(int position) {
        if (expenses.get(position) != null) {
            expenses.get(position).delete();
            expenses.remove(position);
        }
    }

    private void removeRange(int positionStart, int itemCount) {
        for (int position = 0 ; position < itemCount; position++) {
            removeExpenses(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        protected TextView name;
        protected TextView sum;
        protected TextView date;
        protected TextView category;
        protected View selectedOverlay;
        protected CardView cardView;
        private ClickListener clickListener;

        public CardViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_text);
            sum = (TextView) itemView.findViewById(R.id.sum_text);
            category = (TextView) itemView.findViewById(R.id.categories);
            date = (TextView) itemView.findViewById(R.id.date_text);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (clickListener != null) {
                return clickListener.onItemLongClicked(getAdapterPosition());
            }
            return false;
        }


        public interface ClickListener {

            void onItemClicked(int position);

            boolean onItemLongClicked(int position);

        }
    }
}