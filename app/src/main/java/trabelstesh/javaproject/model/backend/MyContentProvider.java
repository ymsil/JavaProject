package trabelstesh.javaproject.model.backend;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {}

    IDB_manager manager = DBManagerFactory.getManager();
    final String TAG = "ProjectContent";

    @Override
    public boolean onCreate()
    {
        Log.d(TAG, "onCreate");
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1)
    {
        Log.d(TAG, "query " + uri.toString());

        String listName = uri.getLastPathSegment();
        switch (listName)
        {
            case "users":
                return manager.GetAllUsers();
            case "businesses":
                return manager.GetAllBusinesses();
            case "activities":
                return manager.GetAllActivities();//
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        Log.d(TAG, "getType " + uri.toString());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        Log.d(TAG, "insert " + uri.toString());

        String listName = uri.getLastPathSegment();
        long id = -1;
        switch (listName)
        {
            case "users":
            {
                id = manager.AddUser(contentValues);
                return ContentUris.withAppendedId(uri, id);
            }
            case "businesses":
            {
                id = manager.AddBusiness(contentValues);
                return ContentUris.withAppendedId(uri, id);
            }
            case "activities":
            {
                id = manager.AddActivity(contentValues);
                return ContentUris.withAppendedId(uri, id);
            }
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings)
    {
        Log.d(TAG, "delete " + uri.toString());

        String listName = uri.getLastPathSegment();
        //long id = ContentUris.parseId(uri);
        long id = Long.parseLong(strings[0]);
        switch (listName)
        {
            case "users":
                if (manager.DeleteUser(id))
                    return 1;
                break;
            case "businesses":
                if (manager.DeleteBusiness(id))
                    return 1;
                break;
            case "activities":
                if (manager.DeleteActivity(id))
                    return 1;
                break;
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings)
    {
        Log.d(TAG, "update " + uri.toString());

        String listName = uri.getLastPathSegment();
//        long id = ContentUris.parseId(uri);
        long id = Long.parseLong(strings[0]);
        switch (listName)
        {
            case "users":
                if (manager.UpdateUser(id, contentValues))
                    return 1;
                break;
            case "businesses":
                if (manager.UpdateBusiness(id, contentValues))
                    return 1;
                break;
            case "activities":
                if (manager.UpdateActivity(id, contentValues))
                    return 1;
                break;
        }
        return 0;
    }
}