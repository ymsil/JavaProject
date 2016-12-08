package trabelstesh.javaproject.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import trabelstesh.javaproject.model.backend.DB_manager;
import trabelstesh.javaproject.model.entities.Business;
import trabelstesh.javaproject.model.entities.User;

/**
 * Created by ymsil on 12/8/2016.
 */

public class List_DBManager implements DB_manager
{
    static List<User> users;
    static List<trabelstesh.javaproject.model.entities.Activity> activities;
    static List<Business> businesses;

    static {
        users = new ArrayList<>();
        activities = new ArrayList<>();
        businesses = new ArrayList<>();
    }

    @Override
    public long AddUser(ContentValues values) {
        User user = Tools.ContentValuesToUser(values);
        users.add(user);
        return user.getId();
    }

    @Override
    public long AddBuisness(ContentValues values) {
        Business business = Tools.ContentValuesToBusiness(values);
        businesses.add(business);
        return business.getId();
    }

    @Override
    public long AddActivity(ContentValues values) {
        trabelstesh.javaproject.model.entities.Activity activity = Tools.ContentValuesToActivity(values);
        activities.add(activity);
        return activity.getId();
    }

    @Override
    public boolean IsNewActivityOrBuisness() {
        return false;
    }

    @Override
    public Cursor GetAllUsers() {
        return Tools.UserListToCursor(users);
    }

    @Override
    public Cursor GetAllBusinesses() {
        return Tools.BusinessListToCursor(businesses);
    }

    @Override
    public Cursor GetAllActivities() {
        return Tools.ActivityListToCursor(activities);
    }

    @Override
    public boolean isNewData() {
        return false;
    }
}
