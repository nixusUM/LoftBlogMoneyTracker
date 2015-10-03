package ru.loftblog.loftblogmoneytracker.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.ui.fragments.CategoriesFragment_;
import ru.loftblog.loftblogmoneytracker.ui.fragments.ExpensesFragment_;
import ru.loftblog.loftblogmoneytracker.ui.fragments.SettingsFragment_;
import ru.loftblog.loftblogmoneytracker.ui.fragments.StatisticsFragment_;
import ru.loftblog.loftblogmoneytracker.utils.GetCategories;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    @Bean
    GetCategories getCategoties;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.frame_container)
    View container;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.navigation_view)
    NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createCategories();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ExpensesFragment_()).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.action_settings) {
            return true;
        }else if (menuItem.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(navView)){
                drawerLayout.closeDrawers();
            }else drawerLayout.openDrawer(navView);
        } return super.onOptionsItemSelected(menuItem);
    }

    @AfterViews
    void initToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Background
    public void createCategories() {
            getCategoties.fromSite(this);
        }

    @AfterViews
    void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectItem(menuItem);
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

     private void selectItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.drawer_expenses:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ExpensesFragment_()).addToBackStack(null).commit();
                break;
            case R.id.drawer_categories:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new CategoriesFragment_()).addToBackStack(null).commit();
                break;
            case R.id.drawer_statistics:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new StatisticsFragment_()).addToBackStack(null).commit();
                break;
            case R.id.drawer_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SettingsFragment_()).addToBackStack(null).commit();
                break;
        }
    }
}