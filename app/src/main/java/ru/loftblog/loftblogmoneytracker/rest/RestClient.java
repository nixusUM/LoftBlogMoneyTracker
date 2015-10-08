package ru.loftblog.loftblogmoneytracker.rest;

import retrofit.RestAdapter;
import ru.loftblog.loftblogmoneytracker.rest.api.CategoryWorkAPI;
import ru.loftblog.loftblogmoneytracker.rest.api.GoogleWorkAPI;
import ru.loftblog.loftblogmoneytracker.rest.api.LoginUserAPI;
import ru.loftblog.loftblogmoneytracker.rest.api.RegisterUserAPI;

public class RestClient {

    private static final String BASE_URL = "http://62.109.17.114";

    private RegisterUserAPI registerUserAPI;

    private LoginUserAPI loginUserAPI;

    private CategoryWorkAPI categoryWorkAPI;

    private GoogleWorkAPI googleWorkAPI;

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        registerUserAPI = restAdapter.create(RegisterUserAPI.class);
        loginUserAPI = restAdapter.create(LoginUserAPI.class);
        categoryWorkAPI = restAdapter.create(CategoryWorkAPI.class);
        googleWorkAPI = restAdapter.create(GoogleWorkAPI.class);
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
}