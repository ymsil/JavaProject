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
    long AddBusiness(ContentValues values);
    int AddActivity(ContentValues values);
    boolean UpdateUser(int id, ContentValues contentValues);
    boolean UpdateBusiness(int id, ContentValues contentValues);
    boolean UpdateActivity(int id, ContentValues contentValues);
    boolean DeleteUser(int id);
    boolean DeleteBusiness(int id);
    boolean DeleteActivity(int id);
    Cursor GetAllUsers();
    Cursor GetAllBusinesses();
    Cursor GetAllActivities();
    boolean isUpdated();
}
