package trabelstesh.javaproject.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;

import java.util.Calendar;
import java.util.List;

import trabelstesh.javaproject.model.backend.MyContract;
import trabelstesh.javaproject.model.entities.Activity;
import trabelstesh.javaproject.model.entities.Business;
import trabelstesh.javaproject.model.entities.Description;
import trabelstesh.javaproject.model.entities.User;

/**
 * Created by ymsil on 12/8/2016.
 */

public class Tools
{

    public static User ContentValuesToUser(ContentValues contentValues) {
        User user = new User();
        user.setId(contentValues.getAsInteger(MyContract.User.USER_ID));
        user.setName(contentValues.getAsString(MyContract.User.USER_NAME));
        user.setPassword(contentValues.getAsString(MyContract.User.USER_PASSWORD));

        return user;
    }

    public static Business ContentValuesToBusiness(ContentValues contentValues) {
        Business business = new Business();
        business.setId(contentValues.getAsInteger(MyContract.Business.BUSINESS_ID));
        business.setName(contentValues.getAsString(MyContract.Business.BUSINESS_NAME));
        business.setAddress(contentValues.getAsString(MyContract.Business.BUSINESS_ADDRESS));
        business.setPhone(contentValues.getAsString(MyContract.Business.BUSINESS_PHONE));
        business.setEmail(contentValues.getAsString(MyContract.Business.BUSINESS_EMAIL));
        business.setWebsite(contentValues.getAsString(MyContract.Business.BUSINESS_WEBSITE));

        return business;
    }

    public static trabelstesh.javaproject.model.entities.Activity ContentValuesToActivity(ContentValues contentValues) {
        Activity activity = new Activity();
        activity.setId(contentValues.getAsInteger(MyContract.Activity.ACTIVITY_ID));
        activity.setDescription(contentValues.getAsString(MyContract.Activity.ACTIVITY_DESCRIPTION));
        activity.setCountry(contentValues.getAsString(MyContract.Activity.ACTIVITY_COUNTRY));
        activity.setStartDate((Calendar) contentValues.get(MyContract.Activity.ACTIVITY_START_DATE));
        activity.setEndDate((Calendar) contentValues.get(MyContract.Activity.ACTIVITY_END_DATE));
        activity.setCost(contentValues.getAsInteger(MyContract.Activity.ACTIVITY_COST));
        activity.setShortDescription(contentValues.getAsString(MyContract.Activity.ACTIVITY_SHORT_DESCRIPTION));
        activity.setBusinessId(contentValues.getAsInteger(MyContract.Activity.ACTIVITY_BUSINESS_ID));

        return activity;
    }

    public static Cursor UserListToCursor(List<User> users)
    {
        String[] columns = new String[]
                {
                        MyContract.User.USER_ID,
                        MyContract.User.USER_NAME,
                        MyContract.User.USER_PASSWORD
                };
                        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (User user : users) {
            matrixCursor.addRow(new Object[]{user.getId(), user.getName(), user.getPassword()});
        }

        return matrixCursor;
    }

    public static Cursor BusinessListToCursor(List<Business> businesses)
    {
        String[] columns = new String[]
                {
                        MyContract.Business.BUSINESS_ID,
                        MyContract.Business.BUSINESS_NAME,
                        MyContract.Business.BUSINESS_ADDRESS,
                        MyContract.Business.BUSINESS_PHONE,
                        MyContract.Business.BUSINESS_EMAIL,
                        MyContract.Business.BUSINESS_WEBSITE,
                };
        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Business business : businesses) {
            matrixCursor.addRow(new Object[]{business.getId(), business.getName(), business.getAddress(),
                    business.getPhone(), business.getEmail(), business.getWebsite()});
        }

        return matrixCursor;
    }

    public static Cursor ActivityListToCursor(List<trabelstesh.javaproject.model.entities.Activity> activities)
    {
        String[] columns = new String[]
                {
                        MyContract.Activity.ACTIVITY_ID,
                        MyContract.Activity.ACTIVITY_DESCRIPTION,
                        MyContract.Activity.ACTIVITY_COUNTRY,
                        MyContract.Activity.ACTIVITY_START_DATE,
                        MyContract.Activity.ACTIVITY_END_DATE,
                        MyContract.Activity.ACTIVITY_COST,
                        MyContract.Activity.ACTIVITY_SHORT_DESCRIPTION,
                        MyContract.Activity.ACTIVITY_BUSINESS_ID,
                };
        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Activity activity : activities) {
            matrixCursor.addRow(new Object[]{activity.getId(), activity.getDescription(), activity.getCountry(),
                activity.getStartDate(), activity.getEndDate(), activity.getCost(), activity.getShortDescription(),
                activity.getBusinessId() });
        }

        return matrixCursor;
    }
}
