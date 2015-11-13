package ru.loftblog.loftblogmoneytracker.rest;

import java.util.ArrayList;

import retrofit.Callback;
import ru.loftblog.loftblogmoneytracker.rest.models.AllCategoriesModel;
import ru.loftblog.loftblogmoneytracker.rest.models.AllExpensesModel;
import ru.loftblog.loftblogmoneytracker.rest.models.BalanceModel;
import ru.loftblog.loftblogmoneytracker.rest.models.CategExpenceModel;
import ru.loftblog.loftblogmoneytracker.rest.models.CategoryData;
import ru.loftblog.loftblogmoneytracker.rest.models.CategoryWorkModel;
import ru.loftblog.loftblogmoneytracker.rest.models.ExpencesWorkModel;
import ru.loftblog.loftblogmoneytracker.rest.models.GoogleWorkModel;
import ru.loftblog.loftblogmoneytracker.rest.models.LogoutModel;
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

    public CategoryWorkModel editCategory (String title, int id, String gToken, String token) {
        return restClient.getCategoryAPI().editCategory(title, id, gToken, token);
    }

    public CategoryWorkModel deleteCategory (Integer id, String gToken, String token) {
        return restClient.getCategoryAPI().deleteCategory(id, gToken, token);
    }

    public GoogleWorkModel workGoogle (String gToken) {
        return restClient.getGoogleWorkAPI().googleJson(gToken);
    }

    public AllCategoriesModel getAllCategories(String gToken, String token) {
        return restClient.getCategoryAPI().getAllCategories(gToken, token);
    }

    public AllExpensesModel getAllExpenses(String gToken, String token) {
        return restClient.getExpensesAPI().getAllExpenses(gToken, token);
    }

    public ExpencesWorkModel addExpense(float sum,  String comment, int categoryId, String trDate, String gToken, String token) {
        return restClient.getExpensesAPI().addExpense(sum, comment, categoryId, trDate, gToken, token);
    }

    public CategExpenceModel getCategExpenceModel(Integer id, String gToken, String token) {
        return restClient.getCategoryAPI().getCategoryWithExpenses(id, gToken, token);
    }

    public ArrayList<CategExpenceModel> getAllCategoriesWithExpenses(String gToken, String token) {
        return restClient.getCategoryAPI().getAllCategoriesWithExpenses(gToken, token);
    }

    public BalanceModel getBalance(String gToken, String token) {
        return restClient.getBalanceAPI().getBalance(gToken, token);
    }

    public BalanceModel setBalance(String sum, String gToken, String token) {
        return restClient.getBalanceAPI().setBalance(sum, gToken, token);
    }

    public void categoriesSync(Integer id, String title, String gToken, String token, Callback<CategoryData> cb) {
        restClient.getCategoryAPI().categoriesSync(id, title, gToken, token, cb);
    }

    public void expensesSync(Integer id, String comment, String sum, String date, String gToken, String token, Callback<CategoryData> cb) {
        restClient.getExpensesAPI().expensesSync(id, comment, sum, date, gToken, token, cb);
    }

    public LogoutModel logout() {
        return restClient.getUserLogoutAPI().logoutUser();
    }
}
