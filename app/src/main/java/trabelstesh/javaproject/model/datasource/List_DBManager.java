package trabelstesh.javaproject.model.datasource;

import android.app.Activity;

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
    static List<Business> businesses;
    static List<Activity> activities;

    static {
        users = new ArrayList<>();
        businesses = new ArrayList<>();
        activities = new ArrayList<>();
    }

    @Override
    public void AddUser() {

    }

    @Override
    public void AddBuisness() {

    }

    @Override
    public void AddActivity() {

    }

    @Override
    public boolean IsNewActivityOrBuisness() {
        return false;
    }

    @Override
    public ArrayList<User> GetAllUsers() {
        return null;
    }

    @Override
    public ArrayList<Business> GetAllBusinesses() {
        return null;
    }

    @Override
    public ArrayList<Activity> GetAllActivities() {
        return null;
    }

    @Override
    public boolean isNewData() {
        return false;
    }
}
