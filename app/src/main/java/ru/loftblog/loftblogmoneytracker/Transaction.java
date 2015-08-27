package ru.loftblog.loftblogmoneytracker;

public class Transaction {

    public String title;
    int sum;
    public String time;

    public Transaction(String title, int sum, String time) {
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
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
