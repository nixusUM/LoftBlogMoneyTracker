package ru.loftblog.loftblogmoneytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ExpensesFragment extends Fragment{

    private ListView listView;
    private TransactionAdapter transactionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expenses_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.main_listview);
        List<Transaction> adapterData = getDataList();
        getActivity().setTitle("Траты");
        transactionAdapter = new TransactionAdapter(getActivity(), adapterData);
        listView.setAdapter(transactionAdapter);
        return view;
    }
    private List<Transaction> getDataList() {
        List<Transaction> data = new ArrayList<>();
        data.add(new Transaction("Phone", 577, new Date()));
        data.add(new Transaction("X-Box", 999, new Date()));
        data.add(new Transaction("TV", 78, new Date()));
        data.add(new Transaction("Super Car", 1150, new Date()));
        data.add(new Transaction("Smart Home", 7555, new Date()));
        data.add(new Transaction("Other things", 355, new Date()));
        return data;
    }
}

