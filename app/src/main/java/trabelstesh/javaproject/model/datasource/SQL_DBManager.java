package trabelstesh.javaproject.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;

import trabelstesh.javaproject.model.backend.IDB_manager;
import trabelstesh.javaproject.model.backend.MyContract;

/**
 * Created by ymsil on 1/14/2017.
 */

public class SQL_DBManager implements IDB_manager
{
    private final String WEB_URL = "http://staszews.vlab.jct.ac.il/php";
    /* TODo: continue implementing updates & deletes */
    public static boolean changed = false;

    @Override
    public long AddUser(ContentValues values)
    {
        try
        {
            String result = PHPtools.POST(WEB_URL + "/addUser.php", values);
            long id = Long.parseLong(result);
            return id;
        } catch (IOException e)
        {
            Log.d(this.getClass().getName(),"\naddUser Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public long AddBusiness(ContentValues values)
    {
        try
        {
            String result = PHPtools.POST(WEB_URL + "/addBusiness.php", values);
            long id = Long.parseLong(result);
            if (id > 0) changed = true;
            return id;
        } catch (IOException e)
        {
            Log.d(this.getClass().getName(),"\naddBusiness Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public long AddActivity(ContentValues values)
    {
        try
        {
            String result = PHPtools.POST(WEB_URL + "/addActivity.php", values);
            long id = Long.parseLong(result);
            if (id > 0) changed = true;
            return id;
        } catch (IOException e)
        {
            Log.d(this.getClass().getName(),"\naddActivity Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public boolean UpdateUser(long id, ContentValues values)
    {
        try
        {
            String result = PHPtools.POST(WEB_URL + "/updateUser.php", values);
            int check = Integer.parseInt(result);
            if (check != 0) return true;
            else return false;
        } catch (IOException e)
        {
            Log.d(this.getClass().getName(),"\nupdateUser Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean UpdateBusiness(long id, ContentValues values)
    {
        try
        {
            String result = PHPtools.POST(WEB_URL + "/updateBusiness.php", values);
            int check = Integer.parseInt(result);
            if (check != 0)
            {
                changed = true;
                return true;
            }
            else return false;
        } catch (IOException e)
        {
            Log.d(this.getClass().getName(),"\nupdateBusiness Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean UpdateActivity(long id, ContentValues values)
    {
        try
        {
            String result = PHPtools.POST(WEB_URL + "/updateActivity.php", values);
            int check = Integer.parseInt(result);
            if (check != 0)
            {
                changed = true;
                return true;
            }
            else return false;
        } catch (IOException e)
        {
            Log.d(this.getClass().getName(),"\nupdateActivity Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean DeleteUser(long id)
    {
        try
        {
            ContentValues cv =new ContentValues();
            cv.put(MyContract.User.USER_ID, id);
            String result = PHPtools.POST(WEB_URL + "/deleteUser.php", cv);
            int check = Integer.parseInt(result);
            if (check != 0) return true;
            else return false;
        } catch (IOException e)
        {
            Log.d(this.getClass().getName(),"\ndeleteUser Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean DeleteBusiness(long id)
    {
        try
        {
            ContentValues cv =new ContentValues();
            cv.put(MyContract.Business.BUSINESS_ID, id);
            String result = PHPtools.POST(WEB_URL + "/deleteBusiness.php", cv);
            int check = Integer.parseInt(result);
            if (check != 0)
            {
                changed = true;
                return true;
            }
            else return false;
        } catch (IOException e)
        {
            Log.d(this.getClass().getName(),"\ndeleteBusiness Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean DeleteActivity(long id)
    {
        try
        {
            ContentValues cv =new ContentValues();
            cv.put(MyContract.Activity.ACTIVITY_ID, id);
            String result = PHPtools.POST(WEB_URL + "/deleteActivity.php", cv);
            int check = Integer.parseInt(result);
            if (check != 0)
            {
                changed = true;
                return true;
            }
            else return false;
        } catch (IOException e)
        {
            Log.d(this.getClass().getName(),"\ndeleteActivity Exception:\n" + e);
            return false;
        }
    }

    @Override
    public Cursor GetAllUsers()
    {
        {
            try {
                MatrixCursor matrixCursor = new MatrixCursor(new String[]
                        {
                                MyContract.User.USER_ID,
                                MyContract.User.USER_NAME,
                                MyContract.User.USER_PASSWORD
                        });
                String str = PHPtools.GET(WEB_URL + "/allUsers.php");
                JSONArray array = new JSONObject(str).getJSONArray("users");


                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = null;

                    jsonObject = array.getJSONObject(i);

                    matrixCursor.addRow(new Object[]{
                            jsonObject.getLong(MyContract.User.USER_ID),
                            jsonObject.getString(MyContract.User.USER_NAME),
                            jsonObject.getString(MyContract.User.USER_PASSWORD),
                    });
                }
                return matrixCursor;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public Cursor GetAllBusinesses()
    {
        try {
            MatrixCursor matrixCursor = new MatrixCursor(new String[]
                    {
                            MyContract.Business.BUSINESS_ID,
                            MyContract.Business.BUSINESS_NAME,
                            MyContract.Business.BUSINESS_ADDRESS,
                            MyContract.Business.BUSINESS_PHONE,
                            MyContract.Business.BUSINESS_EMAIL,
                            MyContract.Business.BUSINESS_WEBSITE
                    });
            String str = PHPtools.GET(WEB_URL + "/allBusinesses.php");
            JSONArray array = new JSONObject(str).getJSONArray("businesses");


            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = null;

                jsonObject = array.getJSONObject(i);

                matrixCursor.addRow(new Object[]{
                        jsonObject.getLong(MyContract.Business.BUSINESS_ID),
                        jsonObject.getString(MyContract.Business.BUSINESS_NAME),
                        jsonObject.getString(MyContract.Business.BUSINESS_ADDRESS),
                        jsonObject.getString(MyContract.Business.BUSINESS_PHONE),
                        jsonObject.getString(MyContract.Business.BUSINESS_EMAIL),
                        jsonObject.getString(MyContract.Business.BUSINESS_WEBSITE)
                });
            }
            return matrixCursor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cursor GetAllActivities()
    {
        try {
            MatrixCursor matrixCursor = new MatrixCursor(new String[]
                    {
                            MyContract.Activity.ACTIVITY_ID,
                            MyContract.Activity.ACTIVITY_DESCRIPTION,
                            MyContract.Activity.ACTIVITY_COUNTRY,
                            MyContract.Activity.ACTIVITY_START_DATE,
                            MyContract.Activity.ACTIVITY_END_DATE,
                            MyContract.Activity.ACTIVITY_COST,
                            MyContract.Activity.ACTIVITY_SHORT_DESCRIPTION,
                            MyContract.Activity.ACTIVITY_BUSINESS_ID
                    });
            String str = PHPtools.GET(WEB_URL + "/allActivities.php");
            JSONArray array = new JSONObject(str).getJSONArray("activities");


            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = null;

                jsonObject = array.getJSONObject(i);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                matrixCursor.addRow(new Object[]{
                        jsonObject.getLong(MyContract.Activity.ACTIVITY_ID),
                        jsonObject.get(MyContract.Activity.ACTIVITY_DESCRIPTION).toString().replaceAll("_"," "),
                        jsonObject.getString(MyContract.Activity.ACTIVITY_COUNTRY),
                        jsonObject.getString(sdf.format(MyContract.Activity.ACTIVITY_START_DATE)),
                        jsonObject.getString(sdf.format(MyContract.Activity.ACTIVITY_END_DATE)),
                        jsonObject.getInt(MyContract.Activity.ACTIVITY_COST),
                        jsonObject.getString(MyContract.Activity.ACTIVITY_SHORT_DESCRIPTION),
                        jsonObject.getLong(MyContract.Activity.ACTIVITY_BUSINESS_ID)
                });
            }
            return matrixCursor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isUpdated()
    {
        if (changed)
        {
            changed = false;
            return true;
        }
        return false;
    }
}
