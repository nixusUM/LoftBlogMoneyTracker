package ru.loftblog.loftblogmoneytracker.ui.activity;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.res.StringRes;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ru.loftblog.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.rest.RestClient;
import ru.loftblog.loftblogmoneytracker.rest.models.GoogleWorkModel;
import ru.loftblog.loftblogmoneytracker.sync.TrackerSyncAdapter;
import ru.loftblog.loftblogmoneytracker.utils.checks.CheckNetworkConnection;
import ru.loftblog.loftblogmoneytracker.utils.checks.GoogleScopes;

@EActivity(R.layout.splash_activity)
public class SplashActivity extends AppCompatActivity implements GoogleScopes {

    @Bean
    CheckNetworkConnection chkConnect;

    @StringRes
    String checkInternet;

    private static final String TAG = "SplashActivity";

    private static int SPLASH_TIME_OUT = 1500;
    private RestClient restClient;
    private String googleToken;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrackerSyncAdapter.initializeSyncAdapter(this);
    }

    @AfterViews
    void showSpalsh() {
        restClient = new RestClient();
        googleToken = MoneyTrackerApp.getGoogleToken(this);
        token = MoneyTrackerApp.getToken(this);
        if (token.equals("1") && googleToken.equals("2")) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent regIntent = new Intent(SplashActivity.this, UserRegistration_.class);
                startActivity(regIntent);
                finish();
                }
                }, SPLASH_TIME_OUT);
          } else {
            if (chkConnect.isOnline(this)) {
                checkTokenValid();
            } else  Toast.makeText(this, checkInternet, Toast.LENGTH_SHORT).show();
        }
    }

// Явно пытался уже указать ему проверить

    @Background
    void checkTokenValid() {
        restClient.getGoogleWorkAPI().tokenStatus(googleToken, new Callback<GoogleWorkModel>() {
            @Override
            public void success(GoogleWorkModel googleWorkModel, Response response) {
                    if (googleWorkModel.getTokenStatus().equalsIgnoreCase("success")) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity_.class);
                        startActivity(intent);
                        finish();
                    } else {
                        doubleTokenCheck();
                    }
                }

            @Override
            public void failure(RetrofitError error) {
                doubleTokenCheck();
            }
        });
    }

    private void doubleTokenCheck() {
        if (chkConnect.isOnline(this)) {
            Intent googleIntent = AccountPicker.newChooseAccountIntent(null, null,
                    new String[]{"com.google"}, false, null, null, null, null);
            startActivityForResult(googleIntent, 111);
        }else  Toast.makeText(this, checkInternet, Toast.LENGTH_SHORT).show();
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == 111 && resultCode == RESULT_OK) {
                getGoogleToken(data);
            }
        }

        @Background
        void getGoogleToken(Intent data) {
            final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            String googleToken = null;
            try {
                googleToken = GoogleAuthUtil.getToken(SplashActivity.this, accountName, SCOPES);
            } catch (IOException e) {
            } catch (final UserRecoverableAuthException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        startActivityForResult(e.getIntent(), 2222);
                    }
                });
            } catch (GoogleAuthException e) {
                e.printStackTrace();
            }

            MoneyTrackerApp.setGoogleToken(SplashActivity.this, googleToken);
            String googleSharedToken = MoneyTrackerApp.getGoogleToken(this);

            Intent intent = new Intent(SplashActivity.this, MainActivity_.class);
            startActivity(intent);
            finish();
    }
}
