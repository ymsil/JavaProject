package trabelstesh.javaproject.controller;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.DBManagerFactory;
import trabelstesh.javaproject.model.backend.IDB_manager;
import trabelstesh.javaproject.model.backend.MyContract;
import trabelstesh.javaproject.model.entities.Activity;
import trabelstesh.javaproject.model.entities.Business;
import trabelstesh.javaproject.model.entities.Description;

public class TroubleActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble);

        AddInitialBusinessAndActivity();
    }

    private void AddInitialBusinessAndActivity() {
        final IDB_manager dbm = DBManagerFactory.getManager();

        Business business = new Business(
                123 ,"Stesh", "Yigal street, Jerusalem, IL",
                "5868349", "ymsil719@gmail.com", "www.google.com");
        ContentValues businessCV = new ContentValues();
        businessCV.put(MyContract.Business.BUSINESS_ID, business.getId());
        businessCV.put(MyContract.Business.BUSINESS_NAME, business.getName());
        businessCV.put(MyContract.Business.BUSINESS_ADDRESS, business.getAddress());
        businessCV.put(MyContract.Business.BUSINESS_PHONE, business.getPhone());
        businessCV.put(MyContract.Business.BUSINESS_EMAIL, business.getEmail());
        businessCV.put(MyContract.Business.BUSINESS_WEBSITE, business.getWebsite());
        dbm.AddBusiness(businessCV);


        Activity activity = new Activity(
                GenerateNewID(dbm.GetAllActivities()), Description.Entertainment_Shows, "IL",
                new Date(), new Date(), 2000, "Avraham Fried Concert", 123);
        ContentValues activityCV = new ContentValues();
        activityCV.put(MyContract.Activity.ACTIVITY_ID, activity.getId());
        activityCV.put(MyContract.Activity.ACTIVITY_DESCRIPTION, activity.getDescription().toString());
        activityCV.put(MyContract.Activity.ACTIVITY_COUNTRY, activity.getCountry());
        activityCV.put(MyContract.Activity.ACTIVITY_START_DATE, new SimpleDateFormat("dd/MM/yyyy").format(activity.getStartDate()));
        activityCV.put(MyContract.Activity.ACTIVITY_END_DATE, new SimpleDateFormat("dd/MM/yyyy").format(activity.getEndDate()));
        activityCV.put(MyContract.Activity.ACTIVITY_COST, activity.getCost());
        activityCV.put(MyContract.Activity.ACTIVITY_SHORT_DESCRIPTION, activity.getShortDescription());
        activityCV.put(MyContract.Activity.ACTIVITY_BUSINESS_ID, activity.getBusinessId());
        dbm.AddActivity(activityCV);
    }

    public void AddBusiness(View view)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("New Business Dialog");
        dialog.setContentView(R.layout.addbusiness_dialog);
        dialog.show();

        final IDB_manager dbm = DBManagerFactory.getManager();

        Button addBusinessButton = (Button)dialog.findViewById(R.id.AddNewBusinessButton);
        Button noNewBusinessButton = (Button)dialog.findViewById(R.id.noNewBusinessButton);

        addBusinessButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                EditText nameText = (EditText)dialog.findViewById(R.id.businessNameText);
                EditText addressText = (EditText)dialog.findViewById(R.id.addressText);
                EditText phoneText = (EditText)dialog.findViewById(R.id.PhoneText);
                EditText emailText = (EditText)dialog.findViewById(R.id.emailText);
                EditText websiteText = (EditText)dialog.findViewById(R.id.webText);

                Business newBusiness = new Business();
                long bId = GenerateNewID(dbm.GetAllBusinesses());
                newBusiness.setId(bId);
                newBusiness.setName(nameText.getText().toString());
                newBusiness.setAddress(addressText.getText().toString());
                newBusiness.setPhone(phoneText.getText().toString());
                newBusiness.setEmail(emailText.getText().toString());
                newBusiness.setWebsite(websiteText.getText().toString());

                ContentValues cv = new ContentValues();
                cv.put(MyContract.Business.BUSINESS_ID, newBusiness.getId());
                cv.put(MyContract.Business.BUSINESS_NAME, newBusiness.getName());
                cv.put(MyContract.Business.BUSINESS_ADDRESS, newBusiness.getAddress());
                cv.put(MyContract.Business.BUSINESS_PHONE, newBusiness.getPhone());
                cv.put(MyContract.Business.BUSINESS_EMAIL, newBusiness.getEmail());
                cv.put(MyContract.Business.BUSINESS_WEBSITE, newBusiness.getWebsite());
                dbm.AddBusiness(cv);

                Toast.makeText(getApplicationContext(), newBusiness.getName() + " Business added", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        noNewBusinessButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),"didn't add any business", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

    }

    public void AddActivity(View view)
    {
        Intent regintent = new Intent(this, AddActivityActivity.class);
        startActivity((regintent));
    }

    public void AllBusinesses(View view)
    {
        Intent regintent = new Intent(this, AllBusinessesActivity.class);
        startActivity((regintent));
    }

    public void AllActivities(View view)
    {
        Intent regintent = new Intent(this, AllActivitiesActivity.class);
        startActivity((regintent));
    }

    private long GenerateNewID(Cursor cursor)
    {
        Random random = new Random();
        long newID = 0;
        boolean isNew = false;
        //int idColumnIndex = cursor.getColumnIndex(MyContract.Business.BUSINESS_ID);

        while (!isNew)
        {
            newID = random.nextInt(1000)+1;
            isNew = true;
            while (cursor.moveToNext())
                if (cursor.getLong(0) == newID) isNew = false;
            if (isNew) return newID;
        }
        return newID;
    }

}
