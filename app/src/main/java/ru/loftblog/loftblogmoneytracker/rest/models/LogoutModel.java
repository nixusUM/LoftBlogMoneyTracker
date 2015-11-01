package ru.loftblog.loftblogmoneytracker.rest.models;

import com.google.gson.annotations.Expose;

public class LogoutModel {

    @Expose
    private String status;

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}

