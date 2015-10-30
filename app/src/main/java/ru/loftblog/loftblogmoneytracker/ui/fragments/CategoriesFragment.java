package ru.loftblog.loftblogmoneytracker.ui.fragments;

import android.app.Dialog;
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
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import ru.loftblog.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftblog.loftblogmoneytracker.adapters.CategoriesAdapter;
import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.database.models.Categories;
import ru.loftblog.loftblogmoneytracker.rest.RestService;
import ru.loftblog.loftblogmoneytracker.rest.models.AllCategoriesModel;
import ru.loftblog.loftblogmoneytracker.rest.models.CategoryData;
import ru.loftblog.loftblogmoneytracker.rest.models.CategoryWorkModel;
import ru.loftblog.loftblogmoneytracker.utils.checks.CheckNetworkConnection;
import ru.loftblog.loftblogmoneytracker.utils.checks.LoginUserStatus;

@EFragment(R.layout.categories_fragment)
public class CategoriesFragment extends Fragment{

    private static final String LOG_TAG = "CategoriesFragment";
    private ActionMode.Callback actionModeCallBack = new ActionModeCallBack();
    private ActionMode actionMode;
    private CategoriesAdapter adapter;

    @ViewById(R.id.recycler_view_content)
    RecyclerView recyclerView;

    @ViewById(R.id.swipe_categor)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @Click()
    void fab() {
        alertDialog();
    }

    @AfterViews
    void setupList() {
        loadData();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        swipeRefreshLayout.setColorSchemeColors(R.color.primary, R.color.black, R.color.fabcolor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllCategories();
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        getActivity().setTitle(getString(R.string.categoriesFragment));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                deleteCategories(adapter.getServId(viewHolder.getAdapterPosition()));
                adapter.removeItem(viewHolder.getAdapterPosition());
                final Snackbar snackbar = Snackbar
                        .make(recyclerView, R.string.deleteRecord , Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void loadData() {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Categories>>() {

            @Override
            public Loader<List<Categories>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Categories>> loader = new AsyncTaskLoader<List<Categories>>(getActivity()) {

                    @Override
                    public List<Categories> loadInBackground() {
                        swipeRefreshLayout.setRefreshing(false);
                        return getDataList();
                    }
                };

                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Categories>> loader, List<Categories> data) {

                adapter = (new CategoriesAdapter(getDataList(), new CategoriesAdapter.CardViewHolder.ClickListener() {
                    @Override
                    public void onItemClicked(int position) {
                        if (actionMode != null) {
                            toggleSelection(position);
                        }
                        if (actionMode == null) {
                            alerDialogEdit(position);
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
                }
                ));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(Loader<List<Categories>> loader) {
            }
        });
    }

    @Background
    void getAllCategories() {
        RestService restService = new RestService();
        if (CheckNetworkConnection.isOnline(getContext())) {
            AllCategoriesModel getCategories = restService.getAllCategories(MoneyTrackerApp.getGoogleToken(getContext()),
                    MoneyTrackerApp.getToken(getContext()));
            if (LoginUserStatus.STATUS_OK.equals(getCategories.getStatus())) {
                for (CategoryData category : getCategories.getCategories()) {
                    Log.e(LOG_TAG, "name: " + category.getTitle() +
                            ", id: " + category.getId());
                }
            }
        }
    }

    @Background
    void deleteCategories(int servId) {
        RestService restService = new RestService();
        if (CheckNetworkConnection.isOnline(getContext())) {
            CategoryWorkModel workModel = restService.deleteCategory(servId, MoneyTrackerApp.getGoogleToken(getContext()),
                    MoneyTrackerApp.getToken(getContext()));
            if (LoginUserStatus.STATUS_OK.equals(workModel.getStatus())) {
                Log.d(LOG_TAG, "Delete category "  + workModel.getCategories().getTitle());
            }
        }
    }

    @Background
    void deleteCategories(List<Integer>  stackCategories) {
        RestService restService = new RestService();
        if (CheckNetworkConnection.isOnline(getContext())) {
            for (int i = 0; i < stackCategories.size(); i++) {
                CategoryWorkModel workModel = restService.deleteCategory(stackCategories.get(i), MoneyTrackerApp.getGoogleToken(getContext()),
                        MoneyTrackerApp.getToken(getContext()));
                if (LoginUserStatus.STATUS_OK.equals(workModel.getStatus())) {
                    Log.d(LOG_TAG, "Delete category "  + workModel.getCategories().getTitle());
                }
            }
        }
    }

    @Background
    public void sendToSiteCategories(String name) {
        RestService restService = new RestService();
        Categories category = new Select().from(Categories.class).where("title = ?", name).executeSingle();
        if (CheckNetworkConnection.isOnline(getContext())) {
            CategoryWorkModel workModel = restService.addCategory(name, MoneyTrackerApp.getGoogleToken(getContext())
                    , MoneyTrackerApp.getToken(getContext()));
            if (LoginUserStatus.STATUS_OK.equals(workModel.getStatus())) {
                category.setServId(workModel.getCategories().getId());
                category.save();
            }
            Log.d(LOG_TAG, "title" + category.getTitle() + "servId" + category.getServId());
        }
    }

    @Background
    public void editCategories(String name, int id) {
        RestService restService = new RestService();
        Categories category = new Select().from(Categories.class).where("title = ?", name).executeSingle();
        if (CheckNetworkConnection.isOnline(getContext())) {
            CategoryWorkModel workModel = restService.editCategory(name, id, MoneyTrackerApp.getGoogleToken(getContext())
                    , MoneyTrackerApp.getToken(getContext()));
            if (LoginUserStatus.STATUS_OK.equals(workModel.getStatus())) {
                category.delete();
                category.setTitle(workModel.getCategories().getTitle());
                category.save();
            }
        }
        Log.d(LOG_TAG, "Respons title" + ":" + category.getTitle());
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

    private List<Categories> getDataList () {
        return new Select().from(Categories.class).execute();
    }

    private void alerDialogEdit(final int id) {
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogStyle);
        dialog.setContentView(R.layout.dialog_category);
        EditText editText = (EditText) dialog.findViewById(R.id.editDialog);
        TextView titleText = (TextView) dialog.findViewById(R.id.titleDialog);
        final Editable text = editText.getText();
        titleText.setText(getResources().getString(R.string.categoriesEdit));
        Button okButton = (Button) dialog.findViewById(R.id.okButton);
        Button cncButton = (Button) dialog.findViewById(R.id.cancelButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adapter.removeItem(id);
                adapter.test(text.toString());
                editCategories(text.toString(), adapter.getServId(id));
                dialog.dismiss();
            }
        });
        cncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    private void alertDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogStyle);
        dialog.setContentView(R.layout.dialog_category);
        EditText editText = (EditText) dialog.findViewById(R.id.editDialog);
        TextView titleText = (TextView) dialog.findViewById(R.id.titleDialog);
        final Editable text = editText.getText();
        titleText.setText(getResources().getString(R.string.categories));
        Button okButton = (Button) dialog.findViewById(R.id.okButton);
        Button cncButton = (Button) dialog.findViewById(R.id.cancelButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Категория: " + text.toString() + " добавлена", Toast.LENGTH_SHORT).show();
                adapter.insertItem(text.toString());
                sendToSiteCategories(text.toString());
                dialog.dismiss();
            }
        });
        cncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    private class ActionModeCallBack implements android.support.v7.view.ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.cab, menu);
            mode.setTitle(getResources().getString(R.string.titleCab));
            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(android.support.v7.view.ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_removed:
                    deleteCategories(adapter.servId(adapter.getSelectedItems()));
                    adapter.removeItems(adapter.getSelectedItems());
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }
        @Override
        public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {
            adapter.clearSelection();
            actionMode = null;
        }
    }
}