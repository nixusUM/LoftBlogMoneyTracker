package ru.loftblog.loftblogmoneytracker.database.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Expenses")
public class Expenses extends Model {

    @Column(name = "price")
    public String price;

    @Column(name = "name")
    public String name;

    @Column(name = "date")
    public String date;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Expenses() {
        super();
    }

    public Expenses (String price, String name, String date, Categories categories){
        super();
        this.price = price;
        this.name = name;
        this.date = date;
        this.categories = categories;
    }

    @Column(name = "Categories", onDelete = Column.ForeignKeyAction.CASCADE)
    public Categories categories;

}