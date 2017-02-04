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
