package ru.loftblog.loftblogmoneytracker.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.rest.RegisterUserStatus;
import ru.loftblog.loftblogmoneytracker.rest.RestService;
import ru.loftblog.loftblogmoneytracker.rest.models.UserRegisterModel;

@EActivity(R.layout.user_registration_layout)
public class UserRegistration extends AppCompatActivity {

    @ViewById
    EditText edLogin, edPassw;

    @OptionsItem(android.R.id.home)
    void back() {
        onBackPressed();
    }

    @Click(R.id.regBtn)
    void chkLogin() {
        if (edLogin.getText().toString().isEmpty()) {
            edLogin.setError("Поле не должно быть пустым!");
            return;
        }
        if (edPassw.getText().toString().isEmpty()) {
            edPassw.setError("Поле не должно быть пустым!");
            return;
        }
        if (isOnline()) {
            regSite();
        } else {
            Toast.makeText(this, "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Background
    void regSite() {

            RestService restService = new RestService();
            UserRegisterModel response = restService.register(edLogin.getText().toString(),
                    edPassw.getText().toString());
            if (RegisterUserStatus.STATUS_OK.equals(response.getStatus())) {
                Intent openActivity = new Intent(this, MainActivity_.class);
                this.startActivity(openActivity);
            } else if (RegisterUserStatus.STATUS_NOT_OK.equals(response.getStatus())) {
                Snackbar.make(findViewById(android.R.id.content), "Логин уже в системе", Snackbar.LENGTH_LONG)
                        .setAction("Ввести другой?", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(
                                        UserRegistration.this,
                                        "Вводите другой логин",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
            }
        }
}

