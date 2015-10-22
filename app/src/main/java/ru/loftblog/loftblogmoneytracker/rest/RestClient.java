package ru.loftblog.loftblogmoneytracker.rest;

import retrofit.RestAdapter;
import ru.loftblog.loftblogmoneytracker.rest.api.BalanceAPI;
import ru.loftblog.loftblogmoneytracker.rest.api.CategoryWorkAPI;
import ru.loftblog.loftblogmoneytracker.rest.api.ExpensesAPI;
import ru.loftblog.loftblogmoneytracker.rest.api.GoogleWorkAPI;
import ru.loftblog.loftblogmoneytracker.rest.api.LoginUserAPI;
import ru.loftblog.loftblogmoneytracker.rest.api.RegisterUserAPI;
import ru.loftblog.loftblogmoneytracker.rest.api.UserLogoutAPI;
import ru.loftblog.loftblogmoneytracker.rest.models.CategExpenceModel;

public class RestClient {

    private static final String BASE_URL = "http://lmt.loftblog.tmweb.ru/";
    private RegisterUserAPI registerUserAPI;
    private LoginUserAPI loginUserAPI;
    private CategoryWorkAPI categoryWorkAPI;
    private GoogleWorkAPI googleWorkAPI;
    private BalanceAPI balanceAPI;
    private ExpensesAPI expensesAPI;
    private UserLogoutAPI userLogoutAPI;

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        registerUserAPI = restAdapter.create(RegisterUserAPI.class);
        loginUserAPI = restAdapter.create(LoginUserAPI.class);
        categoryWorkAPI = restAdapter.create(CategoryWorkAPI.class);
        googleWorkAPI = restAdapter.create(GoogleWorkAPI.class);
        balanceAPI = restAdapter.create(BalanceAPI.class);
        expensesAPI = restAdapter.create(ExpensesAPI.class);
        userLogoutAPI = restAdapter.create(UserLogoutAPI.class);
    }

    public RegisterUserAPI getRegisterUserAPI() {
        return registerUserAPI;
    }

    public LoginUserAPI getLoginUserAPI() {
        return loginUserAPI;
    }

    public CategoryWorkAPI getCategoryAPI() {
        return categoryWorkAPI;
    }

    public GoogleWorkAPI getGoogleWorkAPI() {
        return googleWorkAPI;
    }

    public BalanceAPI getBalanceAPI() {
        return balanceAPI;
    }

    public ExpensesAPI getExpensesAPI() {
        return expensesAPI;
    }

    public UserLogoutAPI getUserLogoutAPI() {
        return userLogoutAPI;
    }
}