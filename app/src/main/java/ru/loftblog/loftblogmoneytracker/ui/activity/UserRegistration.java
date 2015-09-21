package ru.loftblog.loftblogmoneytracker.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.rest.RegisterUserStatus;
import ru.loftblog.loftblogmoneytracker.rest.RestService;
import ru.loftblog.loftblogmoneytracker.rest.models.UserRegisterModel;
import ru.loftblog.loftblogmoneytracker.utils.CheckNetworkConnection;

@EActivity(R.layout.user_registration_layout)
public class UserRegistration extends AppCompatActivity {

    @ViewById
    EditText edRegister, edRegPassw;

    @StringRes
    String etTextEmpty, logText, entAnoth, entAnothlogin, checkInternet;

    @OptionsItem(android.R.id.home)
    void back() {
        onBackPressed();
    }

    @Click(R.id.regBtn)
    void chkLogin() {
        hideKeyboard();
        if (inputCheck())
            if (CheckNetworkConnection.isOnline(this)) {
                regSite();
            } else {
                Toast.makeText(this, checkInternet, Toast.LENGTH_SHORT).show();
            }
    }

    private boolean inputCheck() {

        boolean check = true;

        if (edRegister.getText().toString().isEmpty()) {
            edRegister.setError(etTextEmpty);
            check = false;
        }
        if (edRegPassw.getText().toString().isEmpty()) {
            edRegPassw.setError(etTextEmpty);
            check = false;
        }
        return check;
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Background
    void regSite() {
            RestService restService = new RestService();
            UserRegisterModel register = restService.register(edRegister.getText().toString(),
                    edRegPassw.getText().toString());
            if (RegisterUserStatus.STATUS_OK.equals(register.getStatus())) {
                Intent openActivity = new Intent(this, LoginActivity_.class);
                this.startActivity(openActivity);
            } else if (RegisterUserStatus.STATUS_NOT_OK.equals(register.getStatus())) {
                Snackbar.make(findViewById(android.R.id.content), logText, Snackbar.LENGTH_LONG)
                        .setAction(entAnoth, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(
                                        UserRegistration.this,
                                        entAnothlogin,
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
            }
    }

    @Click(R.id.logToActiv)
    void regActivityOpen() {
        Intent openRegAct = new Intent(this, LoginActivity_.class);
        this.startActivity(openRegAct);
    }
}

