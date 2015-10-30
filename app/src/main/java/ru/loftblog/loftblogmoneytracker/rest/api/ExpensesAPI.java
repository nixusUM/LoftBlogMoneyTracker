package ru.loftblog.loftblogmoneytracker.rest.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftblog.loftblogmoneytracker.rest.models.CategoryData;
import ru.loftblog.loftblogmoneytracker.rest.models.ExpencesWorkModel;

public interface ExpensesAPI {

    @GET("/transactions")
    ExpencesWorkModel getAllExpenses(@Query("google_token") String gToken,
                                    @Query("auth_token") String token);

    @GET("/transactions/add")
    ExpencesWorkModel addExpense(@Query("sum") String sum,
                                @Query("comment") String comment,
                                @Query("category_id") int categoryId,
                                @Query("tr_date") String trDate,
                                @Query("google_token") String gToken,
                                @Query("auth_token") String token);

    @GET("transactions/synch")
    void expensesSync(@Query("data{id}") Integer id,
                      @Query("data[comment]")String comment,
                      @Query("data[sum]")String sum,
                      @Query("data[tr_date]")String date,
                      @Query("google_token") String gToken,
                      @Query("auth_token") String token,
                      Callback<CategoryData> cb);
}