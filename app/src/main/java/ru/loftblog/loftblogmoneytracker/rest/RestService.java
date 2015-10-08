package ru.loftblog.loftblogmoneytracker.rest;

import java.util.List;

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
//
//    public CategoryWorkModel getAllCategories (String gToken, String token) {
//        return restClient.getCategoryAPI().getAllCategories(gToken, token);
//    }

    public GoogleWorkModel workGoogle (String gToken) {
        return restClient.getGoogleWorkAPI().googleJson(gToken);
    }

    public List<CategoryWorkModel> syncCategory(long id, String title) {
        return restClient.getCategoryAPI().syncCategories(id, title);
    }
}
