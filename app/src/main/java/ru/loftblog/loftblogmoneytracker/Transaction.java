package ru.loftblog.loftblogmoneytracker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    private String title;
    private int sum;
    private Date time;

    public Transaction(String title, int sum, Date time) {
        this.title = title;
        this.sum = sum;
        this.time = time;
    }
    public String getTitle() {
        return title;
    }
    public int  getSum() {
        return sum;
    }
    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String time =  dateFormat.format(new Date());
        return time;
    }
}
