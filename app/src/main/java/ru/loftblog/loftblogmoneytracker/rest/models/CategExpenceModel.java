package ru.loftblog.loftblogmoneytracker.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategExpenceModel {

    @Expose
    private String id;
    @Expose
    private String title;
    @SerializedName("transactions")
    @Expose
    private ExpenceData transactions;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The transactions
     */
    public ExpenceData getTransactions() {
        return transactions;
    }

    /**
     *
     * @param transactions
     * The transactions
     */
    public void setTransactions(ExpenceData transactions) {
        this.transactions = transactions;
    }

}

