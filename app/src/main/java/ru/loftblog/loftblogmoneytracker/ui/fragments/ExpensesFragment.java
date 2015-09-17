package ru.loftblog.loftblogmoneytracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import ru.loftblog.loftblogmoneytracker.adapters.ExpensesAdapter;
import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.database.models.Expenses;
import ru.loftblog.loftblogmoneytracker.ui.activity.AddExpenceActivity_;

@EFragment(R.layout.expenses_fragment)
public class ExpensesFragment extends Fragment{

    private ExpensesAdapter expensesAdapter;

    @ViewById(R.id.recycler_view_content)
    RecyclerView recyclerView;

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @Click
    void fab(){
        Intent openActivity = new Intent(getActivity(), AddExpenceActivity_.class);
        getActivity().startActivity(openActivity);
    }

    @AfterViews
    void setupList(){
        expensesAdapter = new ExpensesAdapter(getDataList());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getActivity().setTitle("Траты");
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Expenses>>() {

            @Override
            public Loader<List<Expenses>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Expenses>> loader = new AsyncTaskLoader<List<Expenses>>(getActivity()) {

                    @Override
                    public List<Expenses> loadInBackground() {
                        return getDataList();
                    }
                };

                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished
                    (Loader<List<Expenses>> loader, List<Expenses> data) {
                recyclerView.setAdapter(new ExpensesAdapter(getDataList()));
            }

            @Override
            public void onLoaderReset(Loader<List<Expenses>> loader) {

            }
        });
    }

    private List<Expenses> getDataList () {

        return new Select().from(Expenses.class).execute();
    }
}