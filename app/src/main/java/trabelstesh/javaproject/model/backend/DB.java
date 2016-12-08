package trabelstesh.javaproject.model.backend;

import android.app.Activity;

import java.util.ArrayList;

import trabelstesh.javaproject.model.entities.Business;
import trabelstesh.javaproject.model.entities.User;

/**
 * Created by ymsil on 12/8/2016.
 */

public class DB implements DB_manager
{

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
