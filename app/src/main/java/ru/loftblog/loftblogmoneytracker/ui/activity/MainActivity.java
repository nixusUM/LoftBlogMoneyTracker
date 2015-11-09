package ru.loftblog.loftblogmoneytracker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import ru.loftblog.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.database.models.Categories;
import ru.loftblog.loftblogmoneytracker.rest.RestService;
import ru.loftblog.loftblogmoneytracker.rest.models.AllCategoriesModel;
import ru.loftblog.loftblogmoneytracker.rest.models.CategoryData;
import ru.loftblog.loftblogmoneytracker.rest.models.GoogleWorkModel;
import ru.loftblog.loftblogmoneytracker.ui.fragments.CategoriesFragment_;
import ru.loftblog.loftblogmoneytracker.ui.fragments.ExpensesFragment_;
import ru.loftblog.loftblogmoneytracker.ui.fragments.SettingsFragment_;
import ru.loftblog.loftblogmoneytracker.ui.fragments.StatisticsFragment_;
import ru.loftblog.loftblogmoneytracker.utils.TokenStorage;
import ru.loftblog.loftblogmoneytracker.utils.checks.CheckNetworkConnection;
import ru.loftblog.loftblogmoneytracker.utils.checks.LoginUserStatus;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    @ViewById(R.id.name)
    TextView name;

    @ViewById(R.id.email)
    TextView email;

    @ViewById(R.id.circleView)
    ImageView photo;

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
        getAllCategories();
        defaultCategories();
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
    void getAllCategories() {
        List<Categories> categories = new Select().from(Categories.class).execute();
        RestService restService = new RestService();
        if (CheckNetworkConnection.isOnline(this)) {
            AllCategoriesModel getCategories = restService.getAllCategories(MoneyTrackerApp.getGoogleToken(this),
                    MoneyTrackerApp.getToken(this));
            if (categories.isEmpty()) {
                if (LoginUserStatus.STATUS_OK.equals(getCategories.getStatus())) {
                    for (CategoryData category : getCategories.getCategories()) {
                        Log.e(LOG_TAG, "Category: " + category.getTitle() +
                                ", Category id: " + category.getId());
                        new Categories(category.getTitle(), category.getId()).save();
                    }
                }
            }
        }
    }

    private void defaultCategories() {
        List<Categories> categories = new Select().from(Categories.class).execute();
        if (categories.isEmpty()) {
            new Categories("Fun").save();
            new Categories("Clothes").save();
            new Categories("Food").save();
            new Categories("Travels").save();
        }
    }

    @Background
    void getAccount() {
        RestService restService = new RestService();
        GoogleWorkModel workModel = null;
        workModel = restService.workGoogle(MoneyTrackerApp.getGoogleToken(this));
        if (workModel != null) {
            getDataGoogle(workModel);
        }
    }

    @UiThread
    void getDataGoogle(GoogleWorkModel workModel) {
        name.setText(workModel.getName());
        email.setText(workModel.getEmail());
        Picasso.with(this).load(workModel.getPicture()).into(photo);
        Log.d(LOG_TAG, "main " + workModel.getName());
    }

    @AfterViews
    void setupDrawer() {
        getAccount();
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
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new SettingsFragment_()).addToBackStack(null).commit();
                break;
            case R.id.drawer_logout:
                logoOut();
                break;
        }
    }

    @Background
    void logoOut() {
        RestService restService = new RestService();
        String logout = restService.logout().getStatus();
        if (logout.equals("")) {
            Intent intent = new Intent(this, UserRegistration_.class);
            startActivity(intent);
            finish();
            MoneyTrackerApp.setToken(this, TokenStorage.DEFAULT_TOKEN_KEY);
        }
    }
}