package ru.loftblog.loftblogmoneytracker.database.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Categories")
public class Categories extends Model {

    @Column(name = "title")
    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Categories() {
        super();
    }

    public Categories(String title) {
        super();
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    public List<Expenses> expenses() {
        return getMany(Expenses.class, "Categories");
    }
}
