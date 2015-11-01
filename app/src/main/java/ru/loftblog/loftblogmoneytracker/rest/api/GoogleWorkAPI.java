package ru.loftblog.loftblogmoneytracker.rest.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftblog.loftblogmoneytracker.rest.models.GoogleWorkModel;

public interface GoogleWorkAPI {
    @GET("/gcheck")
    void tokenStatus(@Query("google_token") String gToken, Callback<GoogleWorkModel> tokenCallback);

    @GET("/gjson")
    GoogleWorkModel googleJson(@Query("google_token") String gToken);
}