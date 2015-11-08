package ru.loftblog.loftblogmoneytracker.ui.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

import ru.loftblog.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.database.models.Categories;
import ru.loftblog.loftblogmoneytracker.database.models.Expenses;
import ru.loftblog.loftblogmoneytracker.rest.RestService;
import ru.loftblog.loftblogmoneytracker.rest.models.ExpencesWorkModel;
import ru.loftblog.loftblogmoneytracker.ui.fragments.DatePickerFragment;
import ru.loftblog.loftblogmoneytracker.utils.NotificationUtil;
import ru.loftblog.loftblogmoneytracker.utils.checks.CheckNetworkConnection;
import ru.loftblog.loftblogmoneytracker.utils.checks.LoginUserStatus;

@EActivity(R.layout.activity_add_expence)
public class AddExpenceActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AddExpenceActivity";

    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText etPrice, etDescript;

    @ViewById
    TextView etToday;

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
        setTitle(getString(R.string.addExpenses));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        if (etCategories.getSelectedItem() == null) {
            Toast.makeText(this, R.string.errorToastItem, Toast.LENGTH_SHORT).show();
            return;
        }
        new Expenses(Float.parseFloat(etPrice.getText().toString()), etDescript.getText().toString(), etToday.getText().toString(), (Categories)etCategories.getSelectedItem()).save();
        Toast.makeText(this, getString(R.string.toastExpens) + etDescript.getText().toString() + getString(R.string.toastExpensesAdd), Toast.LENGTH_SHORT).show();
        addExpense(Float.parseFloat(etPrice.getText().toString()), etDescript.getText().toString(), ((Categories) etCategories.getSelectedItem()).getServId(), etToday.getText().toString());
        finish();
        overridePendingTransition(R.anim.from_midle, R.anim.to_midle);
        NotificationUtil.updateNotifications(this);
    }

    @Click(R.id.etToday)
    void getToday() {
        showDatePickerDialog();
    }

    public void showDatePickerDialog() {
        DatePickerFragment datePicker = new DatePickerFragment(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                etToday.setText((String.format("%s.%s.%s", String.valueOf(dayOfMonth), String.valueOf(monthOfYear + 1), String.valueOf(year))));
            }
        };
        datePicker.show(getSupportFragmentManager(), getString(R.string.pickerDialogName));
    }

    @Background
    void addExpense(float sum, String descr, int id, String day) {
        RestService restService = new RestService();
            if (CheckNetworkConnection.isOnline(this)) {
                ExpencesWorkModel workModel = restService.addExpense(sum, descr, id, day, MoneyTrackerApp.getGoogleToken(this)
                        , MoneyTrackerApp.getToken(this));
            if (LoginUserStatus.STATUS_OK.equals(workModel.getStatus()))
                Log.e(LOG_TAG, "Add Expence to server: " + workModel.getId());
        }
    }
}

