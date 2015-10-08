package ru.loftblog.loftblogmoneytracker.rest;

import ru.loftblog.loftblogmoneytracker.rest.models.CategoryWorkModel;
import ru.loftblog.loftblogmoneytracker.rest.models.GoogleWorkModel;
import ru.loftblog.loftblogmoneytracker.rest.models.UserLoginModel;
import ru.loftblog.loftblogmoneytracker.rest.models.UserRegisterModel;

public class RestService {

    private static final String FLAG = "1";

    RestClient restClient;

    public RestService() {
        restClient = new RestClient();
    }

    public UserRegisterModel register (String login, String password) {
        return restClient.getRegisterUserAPI().registerUser(login, password, FLAG);
    }

    public UserLoginModel login (String login, String password) {
        return restClient.getLoginUserAPI().loginUser(login, password);
    }

    public CategoryWorkModel addCategory (String title, String gToken, String token) {
        return restClient.getCategoryAPI().addCategory(title, gToken, token);
    }

    public GoogleWorkModel workGoogle (String gToken) {
        return restClient.getGoogleWorkAPI().googleJson(gToken);
    }
}
