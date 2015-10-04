package ru.loftblog.loftblogmoneytracker.rest;

import ru.loftblog.loftblogmoneytracker.rest.models.AddCategoryModel;
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
    public AddCategoryModel addCategory (String title, String token) {
        return restClient.getCategoryAPI().addCategory(title, token);
    }
}
