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
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import ru.loftblog.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.rest.RestService;
import ru.loftblog.loftblogmoneytracker.rest.models.UserLoginModel;
import ru.loftblog.loftblogmoneytracker.utils.checks.CheckNetworkConnection;
import ru.loftblog.loftblogmoneytracker.utils.checks.CheckNetworkConnection_;
import ru.loftblog.loftblogmoneytracker.utils.checks.CheckUserInput_;

import static ru.loftblog.loftblogmoneytracker.utils.checks.LoginUserStatus.ANOTHER_ERROR;
import static ru.loftblog.loftblogmoneytracker.utils.checks.LoginUserStatus.STATUS_OK;
import static ru.loftblog.loftblogmoneytracker.utils.checks.LoginUserStatus.WRONG_LOGIN;
import static ru.loftblog.loftblogmoneytracker.utils.checks.LoginUserStatus.WRONG_PASSWORD;
import static ru.loftblog.loftblogmoneytracker.utils.checks.LoginUserStatus.WRONG_TOKEN;


@EActivity(R.layout.user_login_layout)
public class LoginActivity extends AppCompatActivity{

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @ViewById
    EditText edLogin, edLoginPassw;

    @StringRes
    String entAnothlogin, entAnoth, errLogin, errPassword, tokenError, anotherError, checkInternet;

    @Bean
    CheckNetworkConnection_ chkConnect;

    @Bean
    CheckUserInput_ checkUserInput;

    @OptionsItem(android.R.id.home)
    void back() {
        onBackPressed();
    }

    @Click(R.id.loginBtn)
    void chkLogin() {
        hideKeyboard();
        if (checkUserInput.inputCheck(edLogin, edLoginPassw)) {
            if (CheckNetworkConnection.isOnline(this)) {
                regSite();
            } else {
                Toast.makeText(this, checkInternet, Toast.LENGTH_SHORT).show();
            }
        }
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
        UserLoginModel login = restService.login(edLogin.getText().toString(),
                edLoginPassw.getText().toString());

        MoneyTrackerApp.setToken(this,login.getAuthToken());

        if (STATUS_OK.equals(login.getStatus())) {
            Intent openActivity = new Intent(this, MainActivity_.class);
            this.startActivity(openActivity);

        } else if(login.getAuthToken().equals(WRONG_TOKEN)) {
            if(login.getAuthToken().equals(WRONG_TOKEN))
                Snackbar.make(findViewById(android.R.id.content), tokenError, Snackbar.LENGTH_LONG).show();
            }
            else {
            switch (login.getStatus()) {
                case WRONG_LOGIN :
                    Snackbar.make(findViewById(android.R.id.content), errLogin, Snackbar.LENGTH_LONG).show();
                    break;
                case WRONG_PASSWORD :
                    Snackbar.make(findViewById(android.R.id.content), errPassword, Snackbar.LENGTH_LONG).show();
                    break;
                case ANOTHER_ERROR :
                    Snackbar.make(findViewById(android.R.id.content), anotherError, Snackbar.LENGTH_LONG).show();
                    break;
                default: break;
            }
        }
    }

    @Click(R.id.regToActiv)
    void regActivityOpen() {
        Intent openRegAct = new Intent(this, UserRegistration_.class);
        this.startActivity(openRegAct);
    }
}


