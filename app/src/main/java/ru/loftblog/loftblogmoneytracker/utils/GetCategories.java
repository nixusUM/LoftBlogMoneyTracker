package ru.loftblog.loftblogmoneytracker.utils;

import android.content.Context;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.EBean;

import ru.loftblog.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftblog.loftblogmoneytracker.database.models.Categories;
import ru.loftblog.loftblogmoneytracker.rest.RestService;
import ru.loftblog.loftblogmoneytracker.rest.models.AddCategoryModel;

@EBean
public class GetCategories {

    public void fromSite (Context context) {
        if (new Select().from(Categories.class).execute().size() == 0) {
            RestService restService = new RestService();
            AddCategoryModel clothes = restService.addCategory("Clothes", MoneyTrackerApp.getToken(context));
            new Categories(clothes.getCategoryAdd().getTitle()).save();
            AddCategoryModel travels = restService.addCategory("Travels", MoneyTrackerApp.getToken(context));
            new Categories(travels.getCategoryAdd().getTitle()).save();
            AddCategoryModel life = restService.addCategory("Life", MoneyTrackerApp.getToken(context));
            new Categories(life.getCategoryAdd().getTitle()).save();
            AddCategoryModel food = restService.addCategory("Food", MoneyTrackerApp.getToken(context));
            new Categories(food.getCategoryAdd().getTitle()).save();
            AddCategoryModel study = restService.addCategory("Study", MoneyTrackerApp.getToken(context));
            new Categories(study.getCategoryAdd().getTitle()).save();
            AddCategoryModel fun = restService.addCategory("Fun", MoneyTrackerApp.getToken(context));
            new Categories(fun.getCategoryAdd().getTitle()).save();
        }
    }
}
