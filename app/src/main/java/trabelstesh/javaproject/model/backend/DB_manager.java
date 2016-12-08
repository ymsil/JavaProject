package trabelstesh.javaproject.model.backend;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import trabelstesh.javaproject.model.entities.Business;
import trabelstesh.javaproject.model.entities.User;

/**
 * Created by ymsil on 12/8/2016.
 */

public interface DB_manager
{
    long AddUser(ContentValues values);
    long AddBuisness(ContentValues values);
    long AddActivity(ContentValues values);
    boolean IsNewActivityOrBuisness();
    Cursor GetAllUsers();
    Cursor GetAllBusinesses();
    Cursor GetAllActivities();
    boolean isNewData();
}
