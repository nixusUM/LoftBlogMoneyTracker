package ru.loftblog.loftblogmoneytracker.rest;

import ru.loftblog.loftblogmoneytracker.rest.models.UserRegisterModel;

public class RestService {

    private static final String FLAG = "1";

    public UserRegisterModel register (String login, String password) {
        RestClient restClient = new RestClient();
        return restClient.getRegisterUserAPI().registerUser(login, password, FLAG);
    }
}
