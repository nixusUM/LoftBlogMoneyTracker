package ru.loftblog.loftblogmoneytracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ExpensesFragment extends Fragment{

    private ExpensesAdapter expensesAdapter;
    List<Expense> data = new ArrayList<>();
    private RecyclerView recyclerView;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expenses_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_content);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        List<Expense> adapterData = getDataList();
        expensesAdapter = new ExpensesAdapter(adapterData);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(expensesAdapter);
        getActivity().setTitle("Траты");
        Snackbar.make(view, getActivity().getTitle(),
                Snackbar.LENGTH_SHORT).show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Клик Траты", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
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

