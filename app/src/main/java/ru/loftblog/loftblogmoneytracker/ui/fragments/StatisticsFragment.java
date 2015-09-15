package ru.loftblog.loftblogmoneytracker.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EFragment;

import ru.loftblog.loftblogmoneytracker.R;

@EFragment(R.layout.statistics_fragment)
public class StatisticsFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Статистика");
        return null;
    }
}

