package ru.loftblog.loftblogmoneytracker.rest;

import retrofit.RestAdapter;
import ru.loftblog.loftblogmoneytracker.rest.api.RegisterUserAPI;

public class RestClient {

    private static final String BASE_URL = "http://62.109.17.114";

    private RegisterUserAPI registerUserAPI;

    RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        registerUserAPI = restAdapter.create(RegisterUserAPI.class);
    }

    public RegisterUserAPI getRegisterUserAPI() {
        return registerUserAPI;
    }
}
