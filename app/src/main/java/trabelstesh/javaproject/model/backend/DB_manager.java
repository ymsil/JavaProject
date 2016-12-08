package trabelstesh.javaproject.model.backend;

import android.app.Activity;

import java.util.ArrayList;

import trabelstesh.javaproject.model.entities.Business;
import trabelstesh.javaproject.model.entities.User;

/**
 * Created by ymsil on 12/8/2016.
 */

public interface DB_manager
{
    public void AddUser();
    public void AddBuisness();
    public void AddActivity();
    public boolean IsNewActivityOrBuisness();
    public ArrayList<User> GetAllUsers();
    public ArrayList<Business> GetAllBusinesses();
    public ArrayList<Activity> GetAllActivities();
    public boolean isNewData();
}
