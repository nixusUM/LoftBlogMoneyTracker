package ru.loftblog.loftblogmoneytracker.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoryWorkModel {

    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<CategoryOptions> categories = new ArrayList<>();

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
    public List<CategoryOptions> getCategories() {
        return categories;
    }

    /**
     *
     * @param categoriesItems
     * The data
     */
    public void setCategories(List<CategoryOptions> categoriesItems) {
        this.categories = categoriesItems;
    }

}