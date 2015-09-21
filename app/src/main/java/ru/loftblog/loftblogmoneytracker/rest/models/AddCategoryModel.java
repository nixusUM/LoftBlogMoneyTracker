package ru.loftblog.loftblogmoneytracker.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCategoryModel {

    @SerializedName("status")
    @Expose

    private String status;
    @SerializedName("data")
    @Expose

    private CategoryAdd categoryAdd;

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
    public CategoryAdd getCategoryAdd() {
        return categoryAdd;
    }

    /**
     *
     * @param categotyAdd
     * The data
     */
    public void setData(CategoryAdd categotyAdd) {
        this.categoryAdd = categotyAdd;
    }
}
