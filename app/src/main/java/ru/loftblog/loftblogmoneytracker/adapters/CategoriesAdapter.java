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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.database.models.Categories;

public class CategoriesAdapter extends SelectableAdapter<CategoriesAdapter.CardViewHolder>  {

    private List<Categories> categories;
    private CardViewHolder.ClickListener clickListener;
    private Context context;
    private int lastPosition = -1;

    public CategoriesAdapter(List<Categories> categories, CardViewHolder.ClickListener clickListener){
        this.categories = categories;
        this.clickListener = clickListener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false);
        context = parent.getContext();
        return new CardViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Categories category = categories.get(position);
        holder.name.setText(category.getTitle());
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
        setAnimation(holder.cardView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_right);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void removeItem(int position) {
        removeCategories(position);
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
                while (positions.size() > count) {
                    count++;
                }
                removeRange(positions.get(count - 1), count);
                for (int i = 0; i < count; i++) {
                    positions.remove(0);
                }
            }
        }
    }

    public int getServId (int position) {
        return categories.get(position).getServId();
    }

    public List<Integer> servId (List<Integer> positions) {
        List<Integer> list = new ArrayList<>();
        for (Integer i : positions) {
            list.add(categories.get(i).getServId());
        }
        return list;
    }

    public void removeCategories(int position) {
        if (categories.get(position) != null) {
            categories.get(position).delete();
            categories.remove(position);
        }
    }

    private void removeRange(int positionStart, int itemCount) {
        for (int position = 0 ; position < itemCount; position++) {
            removeCategories(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    public void test(String name) {
        Categories category = new Categories();
        category.setTitle(name);
        category.save();
        categories.add(category);
        notifyItemChanged(lastPosition);
    }

    public void insertItem(String name) {
        Categories category = new Categories();
        category.setTitle(name);
        category.save();
        categories.add(category);
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
        protected TextView name;
        protected View selectedOverlay;
        private ClickListener clickListener;
        protected CardView cardView;

        public CardViewHolder(View itemView,  ClickListener clickListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.category_item);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            cardView = (CardView) itemView.findViewById(R.id.card_view_cat);
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