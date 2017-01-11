package trabelstesh.javaproject.controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.DBManagerFactory;
import trabelstesh.javaproject.model.backend.IDB_manager;
import trabelstesh.javaproject.model.backend.MyContract;

public class AllActivitiesActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        Cursor allActivities = getContentResolver().query(MyContract.Activity.ACTIVITY_URI, new String[]{},"",new String[]{},"");
        PopulateListView(allActivities);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_activities);

        Cursor allActivities = getContentResolver().query(MyContract.Activity.ACTIVITY_URI, new String[]{},"",new String[]{},"");
        PopulateListView(allActivities);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent regintent = new Intent(this, AddActivityActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addActivityActionButton);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity((regintent));
            }
        });
    }

    private void PopulateListView(Cursor activities)
    {
        // Find ListView to populate
        ListView lvItems = (ListView) findViewById(R.id.activitiesLV);
        // Setup cursor adapter using cursor from last step
        ActivityCursorAdapter activityCursorAdapter = new ActivityCursorAdapter(this, activities);
        // Attach cursor adapter to the ListView
        lvItems.setAdapter(activityCursorAdapter);

        // TODO: 1/11/2017 implement on click listener for updating and deleting activity
//        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                return false;
//            }
//        })
    }
}
