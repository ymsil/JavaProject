package trabelstesh.javaproject.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.MyContract;
import trabelstesh.javaproject.model.entities.Business;

public class BusinessActivity extends AppCompatActivity
{
    BusinessCursorAdapter businessCursorAdapter;
    ArrayList<Business> allBusinesses;
    Cursor listViewRow;
    Dialog dialog;
    ListView lvItems;
    long randomID;
    Business updatedBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
    }

    @Override
    public void onResume()
    {
        super.onResume();
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
                lvItems = (ListView) findViewById(R.id.businessesLV);
                // Setup cursor adapter using cursor from last step
                businessCursorAdapter = new BusinessCursorAdapter(BusinessActivity.this, cursor);
                // Attach cursor adapter to the ListView
                lvItems.setAdapter(businessCursorAdapter);

                dialog = new Dialog(BusinessActivity.this);

                lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
                {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view, int position, long id)
                    {
                        listViewRow = (Cursor) parent.getItemAtPosition(position);

//                        listViewRow = (Cursor) parent.getItemAtPosition((int) parent.getSelectedItemId());
                        dialog.setContentView(R.layout.addbusiness_dialog);
                        dialog.show();

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
//                        // TODO: 1/31/2017 fix problem with update - ask Oshri
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

                                final long bId = listViewRow.getLong(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_ID));
                                final String id = String.valueOf(bId);
                                final ContentValues cv = new ContentValues();
                                cv.put(MyContract.Business.BUSINESS_ID, id);
                                final String name = nameText.getText().toString();
                                cv.put(MyContract.Business.BUSINESS_NAME, name);
                                cv.put(MyContract.Business.BUSINESS_ADDRESS, addressText.getText().toString());
                                cv.put(MyContract.Business.BUSINESS_PHONE, phoneText.getText().toString());
                                cv.put(MyContract.Business.BUSINESS_EMAIL, emailText.getText().toString());
                                cv.put(MyContract.Business.BUSINESS_WEBSITE, websiteText.getText().toString());

                                new AsyncTask<Void, Void, Integer>()
                                {
                                    @Override
                                    protected Integer doInBackground(Void... params)
                                    {
                                        return getContentResolver().update(
                                                MyContract.Business.BUSINESS_URI,
                                                cv,
                                                "_id = ?",
                                                new String[]{id});
                                    }

                                    @Override
                                    protected void onPostExecute(Integer result)
                                    {
                                        super.onPostExecute(result);
                                        if (result > 0)
                                            Toast.makeText(getApplicationContext(), name + " Business updated", Toast.LENGTH_SHORT).show();
                                    }
                                }.execute();
                                dialog.cancel();
                            }
                        });

                        Button deleteBusinessButton = (Button)dialog.findViewById(R.id.noNewBusinessButton);
                        deleteBusinessButton.setText("Delete");
                        deleteBusinessButton.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                new AlertDialog.Builder(BusinessActivity.this)
                                        .setTitle("Delete Business")
                                        .setMessage("Are you sure you want to delete this business?\n" +
                                                "Attention! deleting a business will also delete all it's activities!")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface alertDialog, int which) {
                                                // continue with delete
                                                final long bId = listViewRow.getLong(listViewRow.getColumnIndex(MyContract.Business.BUSINESS_ID));
                                                final String id = String.valueOf(bId);
                                                new AsyncTask<Void, Void, Integer>() {
                                                    @Override
                                                    protected Integer doInBackground(Void... params) {
                                                        return getContentResolver().delete(
                                                                MyContract.Business.BUSINESS_URI,
                                                                "_id = ?",
                                                                new String[]{id});
                                                    }

                                                    @Override
                                                    protected void onPostExecute(Integer result)
                                                    {
                                                        super.onPostExecute(result);
                                                        if (result > 0)
                                                        {
                                                            Toast.makeText(getApplicationContext(), bId + " Business deleted", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }.execute();
                                                alertDialog.cancel();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface alertDialog, int which)
                                            {
                                                // do nothing
                                                alertDialog.cancel();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                                dialog.cancel();
                            }
                        });
                        return false;
                    }
                });
            }
        }.execute();
    }

    public void OnClickAddBusiness(View view)
    {
        dialog = new Dialog(this);
        dialog.setTitle("New Business Dialog");
        dialog.setContentView(R.layout.addbusiness_dialog);
        dialog.show();

        Button noNewBusinessButton = (Button)dialog.findViewById(R.id.noNewBusinessButton);
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

    public void OnClickAddNewBusiness(View view)
    {
        EditText nameText = (EditText)dialog.findViewById(R.id.businessNameText);
        EditText addressText = (EditText)dialog.findViewById(R.id.addressText);
        EditText phoneText = (EditText)dialog.findViewById(R.id.PhoneText);
        EditText emailText = (EditText)dialog.findViewById(R.id.emailText);
        EditText websiteText = (EditText)dialog.findViewById(R.id.webText);

        new AsyncTask<Void, Void, ArrayList<Business>>()
        {
            @Override
            protected ArrayList<Business> doInBackground(Void... params)
            {
                Cursor businesses = getContentResolver().query(MyContract.Business.BUSINESS_URI,null,null,null,null);
                Business business;
                allBusinesses = new ArrayList<>();
                while(businesses.moveToNext())
                {
                    business = new Business();
                    business.setId(businesses.getLong(businesses.getColumnIndex(MyContract.Business.BUSINESS_ID)));
                    business.setName(businesses.getString(businesses.getColumnIndex(MyContract.Business.BUSINESS_NAME)));
                    business.setAddress(businesses.getString(businesses.getColumnIndex(MyContract.Business.BUSINESS_ADDRESS)));
                    business.setPhone(businesses.getString(businesses.getColumnIndex(MyContract.Business.BUSINESS_PHONE)));
                    business.setEmail(businesses.getString(businesses.getColumnIndex(MyContract.Business.BUSINESS_EMAIL)));
                    business.setWebsite(businesses.getString(businesses.getColumnIndex(MyContract.Business.BUSINESS_WEBSITE)));
                    allBusinesses.add(business);
                }
                businesses.close();
                return allBusinesses;
            }
        }.execute();

        randomID = GenerateNewID(allBusinesses);
        Business newBusiness = new Business();
        newBusiness.setId(randomID);
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

        new AsyncTask<Void, Void, Uri>()
        {
            @Override
            protected Uri doInBackground(Void... params)
            {
                return getContentResolver().insert(MyContract.Business.BUSINESS_URI, cv);
            }
        }.execute();
        dialog.cancel();
    }

    private long GenerateNewID(ArrayList<Business> allBusinesses)
    {
        Random random = new Random();
        long newID;
        boolean isNew = false;

        while (!isNew)
        {
            newID = random.nextInt(1000) + 1;
            isNew = true;
            for (Business b : allBusinesses) if (b.getId() == newID) isNew = false;
            if (isNew) return newID;
        }
        return -1;

    }
}
