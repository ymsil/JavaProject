package trabelstesh.javaproject.controller;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.DBManagerFactory;
import trabelstesh.javaproject.model.backend.IDB_manager;
import trabelstesh.javaproject.model.backend.MyContract;
import trabelstesh.javaproject.model.entities.Activity;
import trabelstesh.javaproject.model.entities.Business;
import trabelstesh.javaproject.model.entities.Description;

public class MenuActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //AddInitialBusinessAndActivity();
    }

    @Override
    public void finish()
    {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) this.findViewById(R.id.coordinatorLayout);
        Snackbar end = Snackbar.make(coordinatorLayout,
                "Are you sure you want to exit?", Snackbar.LENGTH_INDEFINITE)
                .setAction("close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        {
                            finishAffinity();
                        }
                    }
                });
        end.show();

    }

    private void AddInitialBusinessAndActivity() {
        final IDB_manager dbm = DBManagerFactory.getManager();

        Business business = new Business(
                123 ,"Stesh", "Yigal street, Jerusalem, IL",
                "5868349", "ymsil719@gmail.com", "www.google.com");
        final ContentValues businessCV = new ContentValues();
        businessCV.put(MyContract.Business.BUSINESS_ID, business.getId());
        businessCV.put(MyContract.Business.BUSINESS_NAME, business.getName());
        businessCV.put(MyContract.Business.BUSINESS_ADDRESS, business.getAddress());
        businessCV.put(MyContract.Business.BUSINESS_PHONE, business.getPhone());
        businessCV.put(MyContract.Business.BUSINESS_EMAIL, business.getEmail());
        businessCV.put(MyContract.Business.BUSINESS_WEBSITE, business.getWebsite());
        new AsyncTask<Void, Void, Uri>() {
            @Override
            protected Uri doInBackground(Void... params) {
                return getContentResolver().insert(MyContract.Business.BUSINESS_URI, businessCV);
            }
        }.execute();

        Activity activity = new Activity(
                456, Description.Entertainment_Shows, "IL",
                new Date(), new Date(), 2000, "Avraham Fried Concert", 123);
        final ContentValues activityCV = new ContentValues();
        activityCV.put(MyContract.Activity.ACTIVITY_ID, activity.getId());
        activityCV.put(MyContract.Activity.ACTIVITY_DESCRIPTION, activity.getDescription().toString());
        activityCV.put(MyContract.Activity.ACTIVITY_COUNTRY, activity.getCountry());
        activityCV.put(MyContract.Activity.ACTIVITY_START_DATE, new SimpleDateFormat("dd/MM/yyyy").format(activity.getStartDate()));
        activityCV.put(MyContract.Activity.ACTIVITY_END_DATE, new SimpleDateFormat("dd/MM/yyyy").format(activity.getEndDate()));
        activityCV.put(MyContract.Activity.ACTIVITY_COST, activity.getCost());
        activityCV.put(MyContract.Activity.ACTIVITY_SHORT_DESCRIPTION, activity.getShortDescription());
        activityCV.put(MyContract.Activity.ACTIVITY_BUSINESS_ID, activity.getBusinessId());
        new AsyncTask<Void, Void, Uri>() {
            @Override
            protected Uri doInBackground(Void... params) {
                return getContentResolver().insert(MyContract.Activity.ACTIVITY_URI, activityCV);
            }
        }.execute();
    }

    public void AllBusinesses(View view)
    {
        Intent businessIntent = new Intent(this, AllBusinessesActivity.class);
//        Intent businessIntent = new Intent(this, BusinessActivity.class);
        startActivity((businessIntent));
    }

    public void AllActivities(View view)
    {
        Intent activityIntent = new Intent(this, AllActivitiesActivity.class);
        startActivity((activityIntent));
    }


}
