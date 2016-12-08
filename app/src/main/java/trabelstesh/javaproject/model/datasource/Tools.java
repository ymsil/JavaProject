package trabelstesh.javaproject.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import trabelstesh.javaproject.model.backend.MyContract;
import trabelstesh.javaproject.model.entities.Business;
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
        business.set(contentValues.getAsString(MyContract.Business.BUSINESS_ADDRESS));
        business.setAddress(contentValues.getAsString(MyContract.Business.BUSINESS_ADDRESS));
        business.setAddress(contentValues.getAsString(MyContract.Business.BUSINESS_ADDRESS));

        return business;
    }

    public static trabelstesh.javaproject.model.entities.Activity ContentValuesToActivity(ContentValues values) {
        return null;
    }

    public static Cursor UserListToCursor(List<User> users) {
        return null;
    }

    public static Cursor BusinessListToCursor(List<Business> businesses) {
        return null;
    }

    public static Cursor ActivityListToCursor(List<trabelstesh.javaproject.model.entities.Activity> activities) {
        return null;
    }
}
