package ru.loftblog.loftblogmoneytracker;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EFragment(R.layout.categories_fragment)
public class CategoriesFragment extends Fragment{
    List<Expense> data = new ArrayList<>();
    private ExpensesAdapter expensesAdapter;
    @ViewById(R.id.recycler_view_content)
    RecyclerView recyclerView;
    @ViewById(R.id.fab)
    FloatingActionButton fab;
    @Click
    void fab(){
        Toast.makeText(getContext(), "Клик категории", Toast.LENGTH_SHORT).show();
    }
    @AfterViews
    void setupList(){
        List<Expense> adapterData = getDataList();
        expensesAdapter = new ExpensesAdapter(adapterData);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(expensesAdapter);
        getActivity().setTitle("Категории");
        Snackbar.make(recyclerView, "Выбраны категории", Snackbar.LENGTH_SHORT).show();
    }
    private List<Expense> getDataList() {
        List<Expense> data = new ArrayList<>();
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        data.add(new Expense("Phone", 11111, new Date()));
        return data;
    }
}

