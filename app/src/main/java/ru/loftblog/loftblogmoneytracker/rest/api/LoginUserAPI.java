package ru.loftblog.loftblogmoneytracker.rest.api;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftblog.loftblogmoneytracker.rest.models.UserLoginModel;

public interface LoginUserAPI {

    @GET("/auth")
    UserLoginModel loginUser(@Query("login") String login,
                             @Query("password") String password);
}
