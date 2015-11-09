package ru.loftblog.loftblogmoneytracker.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.activeandroid.query.Select;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ru.loftblog.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.database.models.Categories;
import ru.loftblog.loftblogmoneytracker.database.models.Expenses;
import ru.loftblog.loftblogmoneytracker.rest.RestService;
import ru.loftblog.loftblogmoneytracker.rest.models.CategoryData;

public class TrackerSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final int SYNC_INTERVAL = 60 * 60 * 24;
    private static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    private static final String LOG_TAG = TrackerSyncAdapter.class.getSimpleName();

    public TrackerSyncAdapter(Context context, boolean autolnitialize) {
        super(context, autolnitialize);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.e(LOG_TAG, "StartSync all data");
        categoriesSync();
//        expensesSync();
    }

    public static void syncImmediately(Context context) {

        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED,
                true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL,
                true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    public static Account getSyncAccount(Context context) {

        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account newAccount = new Account(context.getString(R.string.app_name),
                context.getString(R.string.sync_account_type));
        if (null == accountManager.getPassword(newAccount)) {
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {

        TrackerSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount,
                context.getString(R.string.content_authority), true);
        ContentResolver.addPeriodicSync(newAccount,
                context.getString(R.string.content_authority),
                Bundle.EMPTY,
                SYNC_INTERVAL);
        syncImmediately(context);
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {

        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

// we can enable inexact timers in our periodic sync

            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    private void categoriesSync() {
        RestService restService = new RestService();
        List<Categories> categories = new Select().from(Categories.class).execute();
        if (!categories.isEmpty()) {
            for (Categories category : categories) {
                restService.categoriesSync(category.getId().intValue(),
                        category.title,
                        MoneyTrackerApp.getGoogleToken(getContext()),
                        MoneyTrackerApp.getToken(getContext()),
                        new Callback<CategoryData>() {
                            @Override
                            public void success(CategoryData categoryData, Response response) {
                                Log.e(LOG_TAG, "Good");
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.e(LOG_TAG, "Error");
                            }
                        });
            }
        }
    }

//    private void expensesSync() {
//        RestService restService = new RestService();
//        List<Expenses> expenses = new Select().from(Expenses.class).execute();
//        if (!expenses.isEmpty()) {
//            for (Expenses expense : expenses)
//                restService.expensesSync(expense.getId().intValue(),
//                        expense.name,
//                        expense.price,
//                        expense.date,
//                        MoneyTrackerApp.getGoogleToken(getContext()),
//                        MoneyTrackerApp.getToken(getContext()),
//                        new Callback<CategoryData>() {
//                            @Override
//                            public void success(CategoryData categoryData, Response response) {
//                                Log.e(LOG_TAG, "Good");
//                            }
//
//                            @Override
//                            public void failure(RetrofitError error) {
//                                Log.e(LOG_TAG, "Error");
//                            }
//                        });
//        }
//    }
}
