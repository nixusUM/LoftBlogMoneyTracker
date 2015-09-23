package ru.loftblog.loftblogmoneytracker.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import ru.loftblog.loftblogmoneytracker.adapters.CategoriesAdapter;
import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.database.models.Categories;

@EFragment(R.layout.categories_fragment)
public class CategoriesFragment extends Fragment{

    private CategoriesAdapter categoriesAdapter;

    @ViewById(R.id.recycler_view_content)
    RecyclerView recyclerView;

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @AfterViews
    void setupList() {
        categoriesAdapter = new CategoriesAdapter(getDataList());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getActivity().setTitle("Категории");
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Categories>>() {

            @Override
            public Loader<List<Categories>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Categories>> loader = new AsyncTaskLoader<List<Categories>>(getActivity()) {

                    @Override
                    public List<Categories> loadInBackground() {
                        return getDataList();
                    }
                };

                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished
                    (Loader<List<Categories>> loader, List<Categories> data) {
                recyclerView.setAdapter(new CategoriesAdapter(getDataList()));
            }

            @Override
            public void onLoaderReset(Loader<List<Categories>> loader) {
            }
        });
    }

    private List<Categories> getDataList () {

        return new Select().from(Categories.class).execute();
    }
}