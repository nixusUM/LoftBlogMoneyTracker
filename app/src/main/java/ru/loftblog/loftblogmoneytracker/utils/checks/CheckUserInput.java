package ru.loftblog.loftblogmoneytracker.utils.checks;

import android.widget.EditText;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;

@EBean
public class CheckUserInput {

    @StringRes
    String etTextEmpty;

    public boolean inputCheck(EditText edLogin, EditText edLoginPassw) {

        boolean check = true;

        if (edLogin.getText().toString().isEmpty()) {
            edLogin.setError(etTextEmpty);
            check = false;
        }
        if (edLoginPassw.getText().toString().isEmpty()) {
            edLoginPassw.setError(etTextEmpty);
            check = false;
        }
        return check;
    }
}
