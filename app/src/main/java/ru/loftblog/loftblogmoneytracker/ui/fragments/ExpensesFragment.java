package ru.loftblog.loftblogmoneytracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.AsyncTaskLoader;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

import ru.loftblog.loftblogmoneytracker.adapters.ExpensesAdapter;
import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.database.models.Expenses;
import ru.loftblog.loftblogmoneytracker.ui.activity.AddExpenceActivity_;

@EFragment(R.layout.expenses_fragment)
@OptionsMenu(R.menu.search_menu)
public class ExpensesFragment extends Fragment{

    private ActionMode.Callback actionModeCallBack = new ActionModeCallBack();
    private ActionMode actionMode;
    private ExpensesAdapter adapter;
    private final static String FILTER_ID = "filter_id";

    @ViewById(R.id.recycler_view_content)
    RecyclerView recyclerView;

    @ViewById(R.id.swipe_expence)
    SwipeRefreshLayout swipeRefreshLayout;

    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItem;

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @Click
    void fab(){
        Intent openActivity = new Intent(getActivity(), AddExpenceActivity_.class);
        getActivity().startActivity(openActivity);
        getActivity().overridePendingTransition(R.anim.from_midle, R.anim.to_midle);
    }

    @AfterViews
    void setupList(){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        swipeRefreshLayout.setColorSchemeColors(R.color.primary, R.color.black, R.color.fabcolor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData("");
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        getActivity().setTitle(getString(R.string.expenses));
    }

    @Background(delay = 700, id = FILTER_ID)
    void delayedSearch(String filter) {
        loadData(filter);
    }

    private void loadData(final String filter) {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Expenses>>() {
            @Override
            public Loader<List<Expenses>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Expenses>> loader = new AsyncTaskLoader<List<Expenses>>(getActivity()) {
                    @Override
                    public List<Expenses> loadInBackground() {
                        return getDataList(filter);
                    }
                };
                loader.forceLoad();
                return loader;
            }
            @Override
            public void onLoadFinished(Loader<List<Expenses>> loader, List<Expenses> data) {
                swipeRefreshLayout.setRefreshing(false);
                adapter = (new ExpensesAdapter(getDataList(filter), new ExpensesAdapter.CardViewHolder.ClickListener() {
                    @Override
                    public void onItemClicked(int position) {
                        if (actionMode != null) {
                            toggleSelection(position);
                        }
                    }
                    @Override
                    public boolean onItemLongClicked(int position) {
                        if (actionMode == null) {
                            AppCompatActivity activity = (AppCompatActivity) getActivity();
                            actionMode = activity.startSupportActionMode(actionModeCallBack);
                        }
                        toggleSelection(position);
                        return true;
                    }
                }));
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onLoaderReset(Loader<List<Expenses>> loader) {
            }
        });
    }

        @Override
        public void onResume() {
            super.onResume();
            loadData("");


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.removeItem(viewHolder.getAdapterPosition());
                final Snackbar snackbar = Snackbar
                        .make(recyclerView, R.string.deleteRecord , Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void toggleSelection(int position) {
        adapter.toogleSelection(position);
        int count = adapter.getSelectedItemsConts();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private List<Expenses> getDataList (String filter) {
        return new Select()
                .from(Expenses.class)
                .where("name LIKE ?", new String[]{'%' + filter + '%'})
                .execute();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_action));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BackgroundExecutor.cancelAll(FILTER_ID, true);
                delayedSearch(newText);
                return false;
            }
        });
    }

    private class ActionModeCallBack implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.cab, menu);
            mode.setTitle(getResources().getString(R.string.titleCab));
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_removed:
                    adapter.removeItems(adapter.getSelectedItems());
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelection();
            actionMode = null;
        }
    }

}