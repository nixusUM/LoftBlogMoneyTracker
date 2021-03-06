package ru.loftblog.loftblogmoneytracker.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpencesWorkModel {

    @Expose
    private String status;
    @Expose
    private Integer id;
    @SerializedName("data")
    @Expose
    private ExpenceData expenses ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    /**
     *
     * @return
     * The data
     */
    public ExpenceData getExpenses() {
        return expenses;
    }

    /**
     *
     * @param expenses
     * The data
     */
    public void setExpenses(ExpenceData expenses) {
        this.expenses = expenses;
    }
}

