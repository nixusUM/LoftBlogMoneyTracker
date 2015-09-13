package ru.loftblog.loftblogmoneytracker;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.loftblog.loftblogmoneytracker.database.models.Expenses;
import ru.loftblog.loftblogmoneytracker.database.models.Categories;

@EActivity(R.layout.activity_add_expence)
public class AddExpenceActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText etPrice, etDescript, etToday;

    @ViewById
    Spinner etCategories;

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
    public void addExpenceButton() {
        if (etPrice.getText().toString().isEmpty()) {
            Toast.makeText(this, "Нужно ввести сумму!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etDescript.getText().toString().isEmpty()) {
            Toast.makeText(this, "Нужно ввести примечание!", Toast.LENGTH_SHORT).show();
            return;
        }
        new Expenses(etPrice.getText().toString(), etDescript.getText().toString(), getToday(), (Categories)etCategories.getSelectedItem()).save();
        Toast.makeText(this, "Запись с примечанием " + etDescript.getText().toString() + " добавлена!", Toast.LENGTH_SHORT).show();
    }
}