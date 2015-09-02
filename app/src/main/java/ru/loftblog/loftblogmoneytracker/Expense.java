package ru.loftblog.loftblogmoneytracker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {

    private String title;
    private int sum;
    private Date time;

    public Expense(String title, int sum, Date time) {
        this.title = title;
        this.sum = sum;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int  getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }
    public String getTime() {
        Date time = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(time);
        }
    public void setTime(Date time) {
        this.time = time;
    }
}
