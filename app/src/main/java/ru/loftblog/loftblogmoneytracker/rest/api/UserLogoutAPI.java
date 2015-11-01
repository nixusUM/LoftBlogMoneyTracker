package ru.loftblog.loftblogmoneytracker.rest.api;

import retrofit.http.GET;
import ru.loftblog.loftblogmoneytracker.rest.models.LogoutModel;

public interface UserLogoutAPI {

    @GET("/logout")
    LogoutModel logoutUser();
}
