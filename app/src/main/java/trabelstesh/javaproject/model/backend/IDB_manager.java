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

public interface IDB_manager
{
    long AddUser(ContentValues values);
    long AddBusiness(ContentValues values);
    long AddActivity(ContentValues values);
    boolean UpdateUser(long id, ContentValues contentValues);
    boolean UpdateBusiness(long id, ContentValues contentValues);
    boolean UpdateActivity(long id, ContentValues contentValues);
    boolean DeleteUser(long id);
    boolean DeleteBusiness(long id);
    boolean DeleteActivity(long id);
    Cursor GetAllUsers();
    Cursor GetAllBusinesses();
    Cursor GetAllActivities();
    boolean isUpdated();
}
