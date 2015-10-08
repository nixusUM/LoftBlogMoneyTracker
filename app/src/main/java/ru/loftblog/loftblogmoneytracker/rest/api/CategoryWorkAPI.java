package ru.loftblog.loftblogmoneytracker.rest.api;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftblog.loftblogmoneytracker.rest.models.CategoryWorkModel;

public interface CategoryWorkAPI {

    @GET("/categories/add")
    CategoryWorkModel addCategory (@Query("title") String title,@Query("google_token") String gToken,
                                  @Query("auth_token") String token);

    @GET("/categories/edit")
    CategoryWorkModel edCategory (@Query("id") int id,@Query("google_token") String gToken,
                                  @Query("auth_token") String token);

    @GET("/categories/del")
    CategoryWorkModel rmCategories (@Query("id") int id);

    @GET("/categories")
    CategoryWorkModel allCategories (@Query("google_token") String gToken,
                                    @Query("auth_token") String token);
}

