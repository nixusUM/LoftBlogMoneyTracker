package ru.loftblog.loftblogmoneytracker.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryWorkModel {

    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private CategoryData categories;

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
    public CategoryData getCategories() {
        return categories;
    }

    /**
     *
     * @param categoriesItems
     * The data
     */
    public void setCategories(CategoryData categoriesItems) {
        this.categories = categoriesItems;
    }
}