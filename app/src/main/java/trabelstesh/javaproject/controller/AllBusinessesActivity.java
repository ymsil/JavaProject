package trabelstesh.javaproject.controller;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Random;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.MyContract;
import trabelstesh.javaproject.model.entities.Business;

public class AllBusinessesActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_businesses);

        final Dialog dialog = new Dialog(this);
        Cursor allBusinesses = getContentResolver().query(MyContract.Business.BUSINESS_URI, new String[]{},"",new String[]{},"");
        PopulateListView(allBusinesses);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addActionButton = (FloatingActionButton) findViewById(R.id.addActionButton);
        addActionButton.setOnClickListener(new View.OnClickListener()
        {
//            @Override
//            public void onClick(final View view) {
//                Snackbar.make(view, "Add a new business?? ", Snackbar.LENGTH_LONG)
//                        .setAction("Add", new View.OnClickListener()
//                        {
                            @Override
                            public void onClick(View v)
                            {
                                dialog.setTitle("New Business Dialog");
                                dialog.setContentView(R.layout.addbusiness_dialog);
                                dialog.show();

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
                                        Cursor allBusinesses = getContentResolver().query(MyContract.Business.BUSINESS_URI, new String[]{},"",new String[]{},"");
                                        long bId = GenerateNewID(allBusinesses);
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
                                        getContentResolver().insert(MyContract.Business.BUSINESS_URI, cv);

                                        Toast.makeText(getApplicationContext(), newBusiness.getName() + " Business added", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                        // Switch to new cursor and update contents of ListView
                                        allBusinesses = getContentResolver().query(MyContract.Business.BUSINESS_URI,
                                                new String[]{},"",new String[]{},"");
                                        PopulateListView(allBusinesses);
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
//                        }).show();
//            }
        });
    }

    private void PopulateListView(Cursor businesses)
    {
        // Find ListView to populate
        ListView lvItems = (ListView) findViewById(R.id.businessesLV);
        // Setup cursor adapter using cursor from last step
        final BusinessCursorAdapter businessCursorAdapter = new BusinessCursorAdapter(this, businesses);
        // Attach cursor adapter to the ListView
        lvItems.setAdapter(businessCursorAdapter);

        final Dialog dialog = new Dialog(this);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                final Cursor listViewRow = (Cursor) parent.getItemAtPosition(position);
                dialog.setContentView(R.layout.addbusiness_dialog);
                dialog.show();
                //long bId = listViewRow.getLong(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_ID));
                EditText nameText = (EditText)dialog.findViewById(R.id.businessNameText);
                nameText.setText(listViewRow.getString(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_NAME)));
                EditText addressText = (EditText)dialog.findViewById(R.id.addressText);
                addressText.setText(listViewRow.getString(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_ADDRESS)));
                EditText phoneText = (EditText)dialog.findViewById(R.id.PhoneText);
                phoneText.setText(listViewRow.getString(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_PHONE)));
                EditText emailText = (EditText)dialog.findViewById(R.id.emailText);
                emailText.setText(listViewRow.getString(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_EMAIL)));
                EditText websiteText = (EditText)dialog.findViewById(R.id.webText);
                websiteText.setText(listViewRow.getString(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_WEBSITE)));

                Button updateBusinessButton = (Button)dialog.findViewById(R.id.AddNewBusinessButton);
                updateBusinessButton.setText("Update");
                Button deleteBusinessButton = (Button)dialog.findViewById(R.id.noNewBusinessButton);
                deleteBusinessButton.setText("Delete");

                updateBusinessButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        EditText nameText = (EditText)dialog.findViewById(R.id.businessNameText);
                        EditText addressText = (EditText)dialog.findViewById(R.id.addressText);
                        EditText phoneText = (EditText)dialog.findViewById(R.id.PhoneText);
                        EditText emailText = (EditText)dialog.findViewById(R.id.emailText);
                        EditText websiteText = (EditText)dialog.findViewById(R.id.webText);

                        Business updatedBusiness = new Business();
                        long bId = listViewRow.getLong(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_ID));
                        updatedBusiness.setId(bId);
                        updatedBusiness.setName(nameText.getText().toString());
                        updatedBusiness.setAddress(addressText.getText().toString());
                        updatedBusiness.setPhone(phoneText.getText().toString());
                        updatedBusiness.setEmail(emailText.getText().toString());
                        updatedBusiness.setWebsite(websiteText.getText().toString());

                        ContentValues cv = new ContentValues();
                        cv.put(MyContract.Business.BUSINESS_ID, updatedBusiness.getId());
                        cv.put(MyContract.Business.BUSINESS_NAME, updatedBusiness.getName());
                        cv.put(MyContract.Business.BUSINESS_ADDRESS, updatedBusiness.getAddress());
                        cv.put(MyContract.Business.BUSINESS_PHONE, updatedBusiness.getPhone());
                        cv.put(MyContract.Business.BUSINESS_EMAIL, updatedBusiness.getEmail());
                        cv.put(MyContract.Business.BUSINESS_WEBSITE, updatedBusiness.getWebsite());
                        getContentResolver().update(
                                MyContract.Business.BUSINESS_URI,
                                cv,
                                "_id = ?", new String[]{String.valueOf(bId)});
                        Cursor allBusinesses = getContentResolver().query(MyContract.Business.BUSINESS_URI,
                                new String[]{},"",new String[]{},"");
                        PopulateListView(allBusinesses);

                        Toast.makeText(getApplicationContext(), updatedBusiness.getName() + " Business updated", Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                    }
                });

                deleteBusinessButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        long bId = listViewRow.getLong(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_ID));
                        String id = String.valueOf(bId);
                        getContentResolver().delete(
                                MyContract.Business.BUSINESS_URI,
                                "_id = ?",
                                new String[]{id});
                        Cursor allBusinesses = getContentResolver().query(MyContract.Business.BUSINESS_URI,
                                new String[]{},"",new String[]{},"");
                        PopulateListView(allBusinesses);
                        dialog.cancel();
                    }
                });
                return false;
            }

        });
    }

//    public void UpdateListView()
//    {
//        // Switch to new cursor and update contents of ListView
//        Cursor allBusinesses = getContentResolver().query(MyContract.Business.BUSINESS_URI,
//                new String[]{},"",new String[]{},"");
//        //BusinessCursorAdapter.changeCursor(allBusinesses);
//    }

    private long GenerateNewID(Cursor cursor)
    {
        Random random = new Random();
        long newID = 0;
        boolean isNew = false;

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

