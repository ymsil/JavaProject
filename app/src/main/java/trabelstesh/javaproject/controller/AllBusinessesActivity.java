package trabelstesh.javaproject.controller;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.DBManagerFactory;
import trabelstesh.javaproject.model.backend.IDB_manager;

public class AllBusinessesActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_businesses);

        final IDB_manager dbm = DBManagerFactory.getManager();
        PopulateListView(dbm.GetAllBusinesses());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addActionButton = (FloatingActionButton) findViewById(R.id.addActionButton);
        addActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void PopulateListView(Cursor businesses)
    {
        // Find ListView to populate
        ListView lvItems = (ListView) findViewById(R.id.businessesLV);
        // Setup cursor adapter using cursor from last step
        BusinessCursorAdapter businessCursorAdapter = new BusinessCursorAdapter(this, businesses);
        // Attach cursor adapter to the ListView
        lvItems.setAdapter(businessCursorAdapter);
    }

    public void UpdateListView()
    {
        // Switch to new cursor and update contents of ListView
        //BusinessCursorAdapter.changeCursor(DBManagerFactory.getManager().GetAllBusinesses());
    }
}

