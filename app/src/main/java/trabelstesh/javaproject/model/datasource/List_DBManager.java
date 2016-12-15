package trabelstesh.javaproject.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import trabelstesh.javaproject.model.backend.DB_manager;
import trabelstesh.javaproject.model.entities.Activity;
import trabelstesh.javaproject.model.entities.Business;
import trabelstesh.javaproject.model.entities.User;

/**
 * Created by ymsil on 12/8/2016.
 */

public class List_DBManager implements DB_manager
{
    static List<User> users = new ArrayList<>();
    static List<trabelstesh.javaproject.model.entities.Activity> activities = new ArrayList<>();
    static List<Business> businesses = new ArrayList<>();

    static boolean isNewAorB = false;
    static boolean isNewChanges = false;

    @Override
    public int AddUser(ContentValues values) {
        User user = Tools.ContentValuesToUser(values);
        users.add(user);
        isNewChanges = true;
        return user.getId();
    }

    @Override
    public int AddBusiness(ContentValues values) {
        Business business = Tools.ContentValuesToBusiness(values);
        businesses.add(business);
        isNewChanges = true;
        isNewAorB = true;
        return business.getId();
    }

    @Override
    public int AddActivity(ContentValues values) {
        trabelstesh.javaproject.model.entities.Activity activity = Tools.ContentValuesToActivity(values);
        activities.add(activity);
        isNewChanges = true;
        isNewAorB = true;
        return activity.getId();
    }

    public boolean UpdateUser(int id, ContentValues contentValues)
    {
        User user = Tools.ContentValuesToUser(contentValues);
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getId() == id)
            {
                users.get(i).setName(user.getName());
                users.get(i).setPassword(user.getPassword());
                isNewChanges = true;
                return true;
            }
        return false;
    }

    @Override
    public boolean UpdateBusiness(int id, ContentValues contentValues)
    {
        Business business = Tools.ContentValuesToBusiness(contentValues);
        for (int i = 0; i < businesses.size(); i++)
            if (businesses.get(i).getId() == id)
            {
                businesses.get(i).setName(business.getName());
                businesses.get(i).setAddress(business.getAddress());
                businesses.get(i).setPhone(business.getPhone());
                businesses.get(i).setEmail(business.getEmail());
                businesses.get(i).setWebsite(business.getWebsite());
                isNewChanges = true;
                return true;
            }
        return false;
    }

    @Override
    public boolean UpdateActivity(int id, ContentValues contentValues)
    {
        Activity activity = Tools.ContentValuesToActivity(contentValues);
        for (int i = 0; i < activities.size(); i++)
            if (activities.get(i).getId() == id)
            {
                activities.get(i).setDescription(activity.getDescription().toString());
                activities.get(i).setCountry(activity.getCountry());
                activities.get(i).setStartDate(activity.getStartDate());
                activities.get(i).setEndDate(activity.getEndDate());
                activities.get(i).setCost(activity.getCost());
                activities.get(i).setShortDescription(activity.getShortDescription());
                activities.get(i).setBusinessId(activity.getBusinessId());
                isNewChanges = true;
                return true;
            }
        return false;
    }

    @Override
    public boolean DeleteUser(int id)
    {
        User userToDelete = null;
        for (User user : users)
            if (user.getId() == id) {
                userToDelete = user;
                break;
            }
        isNewChanges = true;
        return users.remove(userToDelete);
    }

    @Override
    public boolean DeleteBusiness(int id)
    {
        Business businessToDelete = null;
        for (Business business : businesses)
            if (business.getId() == id) {
                businessToDelete = business;
                break;
            }
        isNewChanges = true;
        return businesses.remove(businessToDelete);
    }

    @Override
    public boolean DeleteActivity(int id)
    {
        Activity activityToDelete = null;
        for (Activity activity : activities)
            if (activity.getId() == id) {
                activityToDelete = activity;
                break;
            }
        isNewChanges = true;
        return activities.remove(activityToDelete);
    }

    @Override
    public Cursor GetAllUsers() {
        return Tools.UserListToCursor(users);
    }

    @Override
    public Cursor GetAllBusinesses() {
        return Tools.BusinessListToCursor(businesses);
    }

    @Override
    public Cursor GetAllActivities() {
        return Tools.ActivityListToCursor(activities);
    }

    @Override
    public boolean isNewData()
    {
        if(isNewChanges)
        {
            isNewChanges = false;
            return  true;
        }
        return false;
    }

    @Override
    public boolean IsNewActivityOrBusiness()
    {
        if(isNewAorB)
        {
            isNewAorB = false;
            return  true;
        }
    return false;
    }
}
