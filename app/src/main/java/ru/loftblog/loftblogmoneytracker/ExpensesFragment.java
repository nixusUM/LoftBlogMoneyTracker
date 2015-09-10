package ru.loftblog.loftblogmoneytracker;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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
        List<Expense> adapterData = getDataList();
        expensesAdapter = new ExpensesAdapter(adapterData);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(expensesAdapter);
        getActivity().setTitle("Траты");
        Snackbar.make(recyclerView, "Выбраны траты", Snackbar.LENGTH_SHORT).show();
    }

    private List<Expense> getDataList() {
        List<Expense> data = new ArrayList<>();
        data.add(new Expense("Phone", 577, new Date()));
        data.add(new Expense("X-Box", 999, new Date()));
        data.add(new Expense("TV", 78, new Date()));
        data.add(new Expense("Super Car", 1150, new Date()));
        data.add(new Expense("Smart Home", 7555, new Date()));
        data.add(new Expense("Other things", 355, new Date()));
        data.add(new Expense("TV", 78, new Date()));
        data.add(new Expense("Super Car", 1150, new Date()));
        data.add(new Expense("Smart Home", 7555, new Date()));
        data.add(new Expense("Other things", 355, new Date()));
        data.add(new Expense("TV", 78, new Date()));
        data.add(new Expense("Super Car", 1150, new Date()));
        data.add(new Expense("Smart Home", 7555, new Date()));
        data.add(new Expense("Other things", 355, new Date()));
        data.add(new Expense("TV", 78, new Date()));
        data.add(new Expense("Super Car", 1150, new Date()));
        data.add(new Expense("Smart Home", 7555, new Date()));
        data.add(new Expense("Other things", 355, new Date()));
        data.add(new Expense("TV", 78, new Date()));
        data.add(new Expense("Super Car", 1150, new Date()));
        data.add(new Expense("Smart Home", 7555, new Date()));
        data.add(new Expense("Other things", 355, new Date()));
        data.add(new Expense("TV", 78, new Date()));
        data.add(new Expense("Super Car", 1150, new Date()));
        data.add(new Expense("Smart Home", 7555, new Date()));
        data.add(new Expense("Other things", 355, new Date()));
        return data;
    }
}

