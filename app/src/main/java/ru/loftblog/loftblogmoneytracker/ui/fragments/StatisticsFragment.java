package ru.loftblog.loftblogmoneytracker.ui.fragments;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ru.loftblog.loftblogmoneytracker.PieChartView;
import ru.loftblog.loftblogmoneytracker.R;

@EFragment(R.layout.statistics_fragment)
public class StatisticsFragment extends Fragment{

    float[] dataPoints = {465, 700, 1193, 344, 100};

    @ViewById(R.id.pieChart)
    PieChartView pieChartView;

    @AfterViews
    void ready () {
        getActivity().setTitle(getString(R.string.statistics));
        pieChartView.setDatapoints(dataPoints);
    }
}

