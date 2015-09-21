package ru.loftblog.loftblogmoneytracker.rest.api;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftblog.loftblogmoneytracker.rest.models.AddCategoryModel;

public interface AddCategoryAPI {

    @GET("/categories/add")
    AddCategoryModel addCategory (@Query("title") String title,
                                  @Query("auth_token") String token);

}
