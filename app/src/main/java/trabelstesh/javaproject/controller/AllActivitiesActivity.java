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

public class AllActivitiesActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        UpdateListView();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_activities);

        UpdateListView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent regintent = new Intent(this, AddActivityActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addActivityActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity((regintent));
            }
        });
    }

    private void UpdateListView()
    {
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params) {
                return getContentResolver().query(MyContract.Activity.ACTIVITY_URI, null, null, null, null, null);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);

                // Find ListView to populate
                ListView lvItems = (ListView) findViewById(R.id.activitiesLV);
                // Setup cursor adapter using cursor from last step
                ActivityCursorAdapter activityCursorAdapter = new ActivityCursorAdapter(AllActivitiesActivity.this, cursor);
                // Attach cursor adapter to the ListView
                lvItems.setAdapter(activityCursorAdapter);

                final Intent regintent = new Intent(AllActivitiesActivity.this, UpdateRemoveActivityActivity.class);
                lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        final Cursor listViewRow = (Cursor) parent.getItemAtPosition(position);
                        long aId = listViewRow.getLong(listViewRow.getColumnIndex(MyContract.Activity.ACTIVITY_ID));
                        Bundle sendRowToActivity = new Bundle();
                        sendRowToActivity.putLong(MyContract.Activity.ACTIVITY_ID, aId);
                        regintent.putExtras(sendRowToActivity);
                        startActivity(regintent);
                        return false;
                    }
                });
            }
        }.execute();
    }
}


