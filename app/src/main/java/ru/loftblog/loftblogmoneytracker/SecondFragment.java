package ru.loftblog.loftblogmoneytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SecondFragment extends Fragment {

    private ListView listView;
    private List<Transaction> data = new ArrayList<>();
    private TransactionAdapter transactionAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.main_listview);
        List<Transaction> adapterData = getDataList();
        getActivity().setTitle("Second Fragment");
        transactionAdapter = new TransactionAdapter(getActivity(), adapterData);
        listView.setAdapter(transactionAdapter);
        return view;
    }
    private List<Transaction> getDataList() {
        data.add(new Transaction("Phone", 577, GetDate()));
        data.add(new Transaction("X-Box", 999, GetDate()));
        data.add(new Transaction("TV", 78, GetDate()));
        data.add(new Transaction("Super Car", 1150, GetDate()));
        data.add(new Transaction("Smart Home", 7555, GetDate()));
        data.add(new Transaction("Other things", 355, GetDate()));
        data.add(new Transaction("Villa in San Andreas", 55555, GetDate()));
        data.add(new Transaction("Travel", 777, GetDate()));
        return data;
    }
    private String GetDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String time =  dateFormat.format(new Date());
        return time;
    }
}

