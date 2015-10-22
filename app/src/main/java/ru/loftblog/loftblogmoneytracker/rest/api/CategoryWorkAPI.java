package ru.loftblog.loftblogmoneytracker.rest.api;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import ru.loftblog.loftblogmoneytracker.rest.models.CategExpenceModel;
import ru.loftblog.loftblogmoneytracker.rest.models.CategoryOptions;
import ru.loftblog.loftblogmoneytracker.rest.models.CategoryWorkModel;

public interface CategoryWorkAPI {

        @GET("/categories/add")
        CategoryWorkModel addCategory(@Query("title") String title,
                                  @Query("google_token") String gToken,
                                  @Query("auth_token") String token);

        @GET("/categories/edit")
        CategoryWorkModel editCategory(@Query("title") String title,
                                   @Query("id") Integer id,
                                   @Query("google_token") String gToken,
                                   @Query("auth_token") String token);

        @GET("/categories/del")
        CategoryWorkModel deleteCategory(@Query("id") Integer id,
                                     @Query("google_token") String gToken,
                                     @Query("auth_token") String token);

        @GET("/categories")
        CategoryWorkModel getAllCategories(@Query("google_token") String gToken,
                                            @Query("auth_token") String token);

        @GET("/categories/{id}")
        CategExpenceModel getCategoryWithExpenses(@Path("id") Integer id,
                                                          @Query("google_token") String gToken,
                                                          @Query("auth_token") String token);

        @GET("categories/synch")
        void categoriesSync(@Query("data{id}") Integer id,
                            @Query("data[title]")String title,
                            @Query("google_token") String gToken,
                            @Query("auth_token") String token,
                            Callback<CategoryOptions> cb);

        @GET("/transcat")
        ArrayList<CategExpenceModel> getAllCategoriesWithExpenses(@Query("google_token") String gToken,
                                                                          @Query("auth_token") String token);
    }