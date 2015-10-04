package ru.loftblog.loftblogmoneytracker.rest;

import retrofit.RestAdapter;
import ru.loftblog.loftblogmoneytracker.rest.api.AddCategoryAPI;
import ru.loftblog.loftblogmoneytracker.rest.api.LoginUserAPI;
import ru.loftblog.loftblogmoneytracker.rest.api.RegisterUserAPI;

public class RestClient {

    private static final String BASE_URL = "http://62.109.17.114";

    private RegisterUserAPI registerUserAPI;

    private LoginUserAPI loginUserAPI;

    private AddCategoryAPI addCategoryAPI;

    RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        registerUserAPI = restAdapter.create(RegisterUserAPI.class);
        loginUserAPI = restAdapter.create(LoginUserAPI.class);
        addCategoryAPI = restAdapter.create(AddCategoryAPI.class);
    }

    public RegisterUserAPI getRegisterUserAPI() {
        return registerUserAPI;
    }

    public LoginUserAPI getLoginUserAPI() {
        return loginUserAPI;
    }

    public AddCategoryAPI getCategoryAPI() {
        return addCategoryAPI;
    }

}
