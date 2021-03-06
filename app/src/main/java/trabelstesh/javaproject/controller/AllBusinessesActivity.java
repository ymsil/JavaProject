package trabelstesh.javaproject.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
    Dialog updateDeleteDialog;
    Business updatedBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_businesses);

        final Dialog dialog = new Dialog(this);
        UpdateListView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.businessesToolbar);
        toolbar.setTitle("Businesses list:");
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

                                        final EditText nameText = (EditText)dialog.findViewById(R.id.businessNameText);
                                        final EditText addressText = (EditText)dialog.findViewById(R.id.addressText);
                                        final EditText phoneText = (EditText)dialog.findViewById(R.id.PhoneText);
                                        final EditText emailText = (EditText)dialog.findViewById(R.id.emailText);
                                        final EditText websiteText = (EditText)dialog.findViewById(R.id.webText);

                                        new AsyncTask<Void, Void, Cursor>() {
                                            @Override
                                            protected Cursor doInBackground(Void... params) {
                                                return getContentResolver().query(MyContract.Business.BUSINESS_URI, null, null, null, null, null);
                                            }

                                            @Override
                                            protected void onPostExecute(Cursor allBusinesses) {
                                                super.onPostExecute(allBusinesses);

                                                final Business newBusiness = new Business();
                                                long bId = GenerateNewID(allBusinesses);
                                                newBusiness.setId(bId);
                                                newBusiness.setName(nameText.getText().toString());
                                                newBusiness.setAddress(addressText.getText().toString());
                                                newBusiness.setPhone(phoneText.getText().toString());
                                                newBusiness.setEmail(emailText.getText().toString());
                                                newBusiness.setWebsite(websiteText.getText().toString());

                                                final ContentValues cv = new ContentValues();
                                                cv.put(MyContract.Business.BUSINESS_ID, newBusiness.getId());
                                                cv.put(MyContract.Business.BUSINESS_NAME, newBusiness.getName());
                                                cv.put(MyContract.Business.BUSINESS_ADDRESS, newBusiness.getAddress());
                                                cv.put(MyContract.Business.BUSINESS_PHONE, newBusiness.getPhone());
                                                cv.put(MyContract.Business.BUSINESS_EMAIL, newBusiness.getEmail());
                                                cv.put(MyContract.Business.BUSINESS_WEBSITE, newBusiness.getWebsite());

                                                new AsyncTask<Void, Void, Uri>() {
                                                    @Override
                                                    protected Uri doInBackground(Void... params) {
                                                        return getContentResolver().insert(MyContract.Business.BUSINESS_URI, cv);
                                                    }

                                                    @Override
                                                    protected void onPostExecute(Uri uri) {
                                                        super.onPostExecute(uri);

                                                        long id = ContentUris.parseId(uri);
                                                        if (id > 0) {
                                                            Toast.makeText(getBaseContext(), newBusiness.getName() + " Business added", Toast.LENGTH_LONG).show();
                                                            UpdateListView();
                                                        }
                                                    }
                                                }.execute();

                                                dialog.cancel();

                                            }
                                        }.execute();
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

    private void UpdateListView()
    {
        new AsyncTask<Void, Void, Cursor>()
        {

            @Override
            protected Cursor doInBackground(Void... params)
            {
                return getContentResolver().query(MyContract.Business.BUSINESS_URI, null, null, null, null, null);
            }

            @Override
            protected void onPostExecute(Cursor cursor)
            {
                super.onPostExecute(cursor);

                // Find ListView to populate
                ListView lvItems = (ListView) findViewById(R.id.businessesLV);
                // Setup cursor adapter using cursor from last step
                BusinessCursorAdapter businessCursorAdapter = new BusinessCursorAdapter(AllBusinessesActivity.this, cursor);
                // clean List View
                lvItems.setAdapter(null);
                // Attach cursor adapter to the ListView
                lvItems.setAdapter(businessCursorAdapter);

                updateDeleteDialog = new Dialog(AllBusinessesActivity.this);
                lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
                {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
                    {

                        Toast.makeText(getApplicationContext(), "notice! deleting a business will delete all connected activities", Toast.LENGTH_SHORT).show();


                        Cursor listViewRow = (Cursor) parent.getItemAtPosition(position);
                        final long bId = listViewRow.getLong(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_ID));

                        updateDeleteDialog.setContentView(R.layout.addbusiness_dialog);
                        EditText nameText = (EditText)updateDeleteDialog.findViewById(R.id.businessNameText);
                        nameText.setText(listViewRow.getString(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_NAME)));
                        EditText addressText = (EditText)updateDeleteDialog.findViewById(R.id.addressText);
                        addressText.setText(listViewRow.getString(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_ADDRESS)));
                        EditText phoneText = (EditText)updateDeleteDialog.findViewById(R.id.PhoneText);
                        phoneText.setText(listViewRow.getString(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_PHONE)));
                        EditText emailText = (EditText)updateDeleteDialog.findViewById(R.id.emailText);
                        emailText.setText(listViewRow.getString(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_EMAIL)));
                        EditText websiteText = (EditText)updateDeleteDialog.findViewById(R.id.webText);
                        websiteText.setText(listViewRow.getString(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_WEBSITE)));

                        Button updateBusinessButton = (Button)updateDeleteDialog.findViewById(R.id.AddNewBusinessButton);
                        updateBusinessButton.setText("Update");
                        Button deleteBusinessButton = (Button)updateDeleteDialog.findViewById(R.id.noNewBusinessButton);
                        deleteBusinessButton.setText("Delete");

                        updateDeleteDialog.show();

                        updateBusinessButton.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {

                                EditText nameText = (EditText)updateDeleteDialog.findViewById(R.id.businessNameText);
                                EditText addressText = (EditText)updateDeleteDialog.findViewById(R.id.addressText);
                                EditText phoneText = (EditText)updateDeleteDialog.findViewById(R.id.PhoneText);
                                EditText emailText = (EditText)updateDeleteDialog.findViewById(R.id.emailText);
                                EditText websiteText = (EditText)updateDeleteDialog.findViewById(R.id.webText);

                                updatedBusiness = new Business();
                                updatedBusiness.setId(bId);
                                updatedBusiness.setName(nameText.getText().toString());
                                updatedBusiness.setAddress(addressText.getText().toString());
                                updatedBusiness.setPhone(phoneText.getText().toString());
                                updatedBusiness.setEmail(emailText.getText().toString());
                                updatedBusiness.setWebsite(websiteText.getText().toString());

                                final ContentValues cv = new ContentValues();
                                cv.put(MyContract.Business.BUSINESS_ID, updatedBusiness.getId());
                                cv.put(MyContract.Business.BUSINESS_NAME, updatedBusiness.getName());
                                cv.put(MyContract.Business.BUSINESS_ADDRESS, updatedBusiness.getAddress());
                                cv.put(MyContract.Business.BUSINESS_PHONE, updatedBusiness.getPhone());
                                cv.put(MyContract.Business.BUSINESS_EMAIL, updatedBusiness.getEmail());
                                cv.put(MyContract.Business.BUSINESS_WEBSITE, updatedBusiness.getWebsite());

                                new AsyncTask<Void, Void, Integer>() {
                                    @Override
                                    protected Integer doInBackground(Void... params) {
                                        return getContentResolver().update(
                                                MyContract.Business.BUSINESS_URI,
                                                cv,
                                                "_id = ?", new String[]{String.valueOf(bId)});
                                    }

                                    @Override
                                    protected void onPostExecute(Integer result)
                                    {
                                        super.onPostExecute(result);
                                        Toast.makeText(getApplicationContext(), updatedBusiness.getName() + " Business updated", Toast.LENGTH_SHORT).show();
                                    }
                                }.execute();
                                updateDeleteDialog.cancel();
                                UpdateListView();
                            }
                        });

                        deleteBusinessButton.setOnClickListener(new View.OnClickListener()
                        {

                            @Override
                            public void onClick(View v)
                            {
                                final String id = String.valueOf(bId);
//                                new AlertDialog.Builder(AllBusinessesActivity.this)
//                                        .setTitle("Delete Business")
//                                        .setMessage("Are you sure you want to delete this business?\n" +
//                                                "notice! deleting a business will delete all connected activities.")
//                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
//                                        {
//                                            public void onClick(DialogInterface dialog, int which)
//                                            {
//
//                                            }
//                                        })
//                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//
//                                            }
//                                        })
//                                        .setIcon(android.R.drawable.ic_dialog_alert)
//                                        .show();
                                new AsyncTask<Void, Void, Integer>() {
                                    @Override
                                    protected Integer doInBackground(Void... params) {
                                        return getContentResolver().delete(
                                                MyContract.Business.BUSINESS_URI,
                                                "_id = ?",
                                                new String[]{id});
                                    }
                                }.execute();
                                Toast.makeText(getApplicationContext(), "business deleted", Toast.LENGTH_SHORT).show();
                                updateDeleteDialog.cancel();
                                UpdateListView();
                            }
                        });
                        return false;
                    }

                });
            }
        }.execute();

    }


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

