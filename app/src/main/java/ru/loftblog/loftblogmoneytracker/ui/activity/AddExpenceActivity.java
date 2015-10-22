package ru.loftblog.loftblogmoneytracker.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.loftblog.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.database.models.Categories;
import ru.loftblog.loftblogmoneytracker.database.models.Expenses;
import ru.loftblog.loftblogmoneytracker.rest.RestService;
import ru.loftblog.loftblogmoneytracker.rest.models.ExpenceOptions;
import ru.loftblog.loftblogmoneytracker.rest.models.ExpencesWorkModel;
import ru.loftblog.loftblogmoneytracker.utils.checks.LoginUserStatus;

@EActivity(R.layout.activity_add_expence)
public class AddExpenceActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AddExpenceActivity";

    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText etPrice, etDescript;

    @ViewById
    Spinner etCategories;

    @StringRes
    String etTextEmpty;

    @OptionsItem(android.R.id.home)
    void back() {
        onBackPressed();
    }

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Добавить трату");
    }

    private List<Categories> getCategories() {
        return new Select().from(Categories.class).execute();
    }

    @AfterViews
    void categories () {
        ArrayAdapter<Categories> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getCategories());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etCategories.setAdapter(adapter);
        etCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String getToday() {
        Date time = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(time);
    }

    @Click(R.id.addBtnExpence)
    void addExpenceButton() {

        if (etPrice.getText().toString().isEmpty()) {
            etPrice.setError(etTextEmpty);
            return;
        }
        if (etDescript.getText().toString().isEmpty()) {
            etDescript.setError(etTextEmpty);
            return;
        }

        new Expenses(etPrice.getText().toString(), etDescript.getText().toString(), getToday(), (Categories)etCategories.getSelectedItem()).save();
        Toast.makeText(this, "Запись с примечанием " + etDescript.getText().toString() + " добавлена!", Toast.LENGTH_SHORT).show();
        addExpense();
    }

    @Background
    void addExpense() {
        Categories categories = new Categories();
        RestService restService = new RestService();
        if ((etCategories.getSelectedItem() == categories.getTitle())) {
            ExpencesWorkModel addExpenseResp = restService.addExpense(etPrice.getText().toString(), etDescript.getText().toString(), categories.getId(), getToday(),
                    MoneyTrackerApp.getGoogleToken(this), MoneyTrackerApp.getToken(this));
            if (LoginUserStatus.STATUS_OK.equals(addExpenseResp.getStatus()))
                Log.e(LOG_TAG, "Add Expence to server: " + addExpenseResp.getId());
        }
    }
}