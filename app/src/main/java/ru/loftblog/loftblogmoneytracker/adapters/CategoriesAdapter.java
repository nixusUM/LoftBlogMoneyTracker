package ru.loftblog.loftblogmoneytracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.database.models.Categories;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CardViewHolder>  {

    List<Categories> categories;

    public CategoriesAdapter(List<Categories> categories){
        this.categories = categories;
    }
    @Override

    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false );
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Categories category = categories.get(position);
        holder.name.setText(category.getTitle());
        holder.hashCode();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        public CardViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.category_item);
        }
    }
}