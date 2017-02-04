package trabelstesh.javaproject.controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.MyContract;

public class AllActivitiesActivity extends AppCompatActivity
{
    Intent addActivityIntent;
    Intent updateDeleteActivityIntent;

    @Override
    protected void onResume()
    {
        super.onResume();

        new AsyncTask<Void, Void, Cursor>()
        {
            @Override
            protected Cursor doInBackground(Void... params)
            {
                return getContentResolver().query(MyContract.Activity.ACTIVITY_URI, null, null, null, null, null);
            }

            @Override
            protected void onPostExecute(Cursor activities)
            {
                super.onPostExecute(activities);
//                allActivities = activities;
                // Find ListView to populate
                ListView lvItems = (ListView) findViewById(R.id.activitiesLV);
                // Setup cursor adapter using cursor from last step
                ActivityCursorAdapter activityCursorAdapter = new ActivityCursorAdapter(AllActivitiesActivity.this, activities);
                // Attach cursor adapter to the ListView
                lvItems.setAdapter(activityCursorAdapter);

                updateDeleteActivityIntent = new Intent(AllActivitiesActivity.this, UpdateRemoveActivityActivity.class);

                lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        final Cursor listViewRow = (Cursor) parent.getItemAtPosition(position);
                        long aId = listViewRow.getLong(listViewRow.getColumnIndex(MyContract.Activity.ACTIVITY_ID));
                        Bundle sendRowToActivity = new Bundle();

                        sendRowToActivity.putLong(MyContract.Activity.ACTIVITY_ID, aId);
                        updateDeleteActivityIntent.putExtras(sendRowToActivity);
                        startActivity(updateDeleteActivityIntent);
                        return false;
                    }
                });
            }
        }.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_activities);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activitiesToolbar);
        toolbar.setTitle("Activities list:");
        setSupportActionBar(toolbar);

        addActivityIntent = new Intent(this, AddActivityActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addActivityActionButton);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity((addActivityIntent));
            }
        });
    }
}


