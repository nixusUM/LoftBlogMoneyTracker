package ru.loftblog.loftblogmoneytracker.ui.fragments;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import ru.loftblog.loftblogmoneytracker.R;

@EFragment(R.layout.settings_fragment)
public class SettingsFragment extends Fragment{

    @AfterViews
    void ready () {
        getActivity().setTitle(getString(R.string.statistics));
    }
}
