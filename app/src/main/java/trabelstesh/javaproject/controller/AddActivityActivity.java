package trabelstesh.javaproject.controller;

import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.MyContract;
import trabelstesh.javaproject.model.entities.Activity;
import trabelstesh.javaproject.model.entities.Description;

public class AddActivityActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        Spinner ActivitySpinner = (Spinner) findViewById(R.id.descriptionSpinner);
        String[] Desc = DescriptionWithSpaces(Description.values());
        ActivitySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Desc));
        TextView startDateText = (TextView) findViewById(R.id.startDateText);
        TextView endDateText = (TextView) findViewById(R.id.endDateText);
        String today = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        startDateText.setText(today);
        endDateText.setText(today);

        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params)
            {
                return getContentResolver().query(MyContract.Business.BUSINESS_URI,null, null, null, null, null);
            }

            @Override
            protected void onPostExecute(Cursor allBusinesses)
            {
                super.onPostExecute(allBusinesses);

                int businessNameColumnIndex = allBusinesses.getColumnIndex(MyContract.Business.BUSINESS_NAME);
                int i = 0;
                String [] businessNames = new String[allBusinesses.getCount()];
                while (allBusinesses.moveToNext())
                {
                    businessNames[i++] = allBusinesses.getString(businessNameColumnIndex);
                }
                Spinner BusinessSpinner = (Spinner) findViewById(R.id.businessSpinner);
                BusinessSpinner.setAdapter(new ArrayAdapter<>(AddActivityActivity.this, android.R.layout.simple_spinner_item, businessNames));
            }
        }.execute();
    }

    public void AddActivity(View view) throws Exception
    {
        Spinner descriptionSpinner =(Spinner) findViewById(R.id.descriptionSpinner);
        final String description = descriptionSpinner.getSelectedItem().toString().replaceAll(" ", "_");
        final EditText countryText = (EditText)findViewById(R.id.countryEditText);
        TextView startDateText = (TextView)findViewById(R.id.startDateText);
        final String startDate = startDateText.getText().toString();
        TextView endDateText = (TextView) findViewById(R.id.endDateText);
        final String endDate = endDateText.getText().toString();
        final EditText costText = (EditText)findViewById(R.id.CostText);
        final EditText shortDescText = (EditText)findViewById(R.id.shortDescText);
        Spinner bIdSpinner = (Spinner)findViewById(R.id.businessSpinner);
        final String bName = bIdSpinner.getSelectedItem().toString();

        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params) {
                return getContentResolver().query(MyContract.Business.BUSINESS_URI, null, null, null, null, null);
            }

            @Override
            protected void onPostExecute(Cursor allBusinesses)
            {
                super.onPostExecute(allBusinesses);

                final Long bId = FindBIdByName(bName, allBusinesses);
                if (bId < 0) try {
                    throw new Exception("Problem with business ID");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new AsyncTask<Void, Void, Cursor>()
                {
                    @Override
                    protected Cursor doInBackground(Void... params) {
                        return getContentResolver().query(MyContract.Activity.ACTIVITY_URI, null, null, null, null, null);
                    }

                    @Override
                    protected void onPostExecute(Cursor allActivities) {
                        super.onPostExecute(allActivities);

                        Activity newActivity = new Activity();
                        long aId = GenerateNewID(allActivities);
                        newActivity.setId(aId);
                        newActivity.setDescription(description);
                        newActivity.setCountry(countryText.getText().toString());
                        newActivity.setStartDate(startDate);
                        newActivity.setEndDate(endDate);
                        newActivity.setCost(Integer.parseInt(costText.getText().toString()));
                        newActivity.setShortDescription(shortDescText.getText().toString());
                        newActivity.setBusinessId(bId);

                        final ContentValues cv = new ContentValues();
                        cv.put(MyContract.Activity.ACTIVITY_ID, newActivity.getId());
                        cv.put(MyContract.Activity.ACTIVITY_DESCRIPTION, newActivity.getDescription().toString());
                        cv.put(MyContract.Activity.ACTIVITY_COUNTRY, newActivity.getCountry());
                        cv.put(MyContract.Activity.ACTIVITY_START_DATE, (newActivity.getStartDate()));
                        cv.put(MyContract.Activity.ACTIVITY_END_DATE, (newActivity.getEndDate()));
                        cv.put(MyContract.Activity.ACTIVITY_COST, newActivity.getCost());
                        cv.put(MyContract.Activity.ACTIVITY_SHORT_DESCRIPTION, newActivity.getShortDescription());
                        cv.put(MyContract.Activity.ACTIVITY_BUSINESS_ID, newActivity.getBusinessId());

                        new AsyncTask<Void, Void, Uri>() {
                            @Override
                            protected Uri doInBackground(Void... params) {
                                return getContentResolver().insert(MyContract.Activity.ACTIVITY_URI, cv);
                            }

                            @Override
                            protected void onPostExecute(Uri uri) {
                                super.onPostExecute(uri);

                                long id = ContentUris.parseId(uri);
                                if (id > 0) {
                                    Toast.makeText(getApplicationContext(), "activity added", Toast.LENGTH_SHORT).show();
                                    AddActivityActivity.this.finish();
                                }

                            }
                        }.execute();
                    }
                }.execute();
            }
        }.execute();
    }

    public void Back(View view)
    {
        Toast.makeText(getApplicationContext(),"didn't add any activities", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    private String[] DescriptionWithSpaces(Description[] values)
    {
        String stringValue;
        String[] newDesc = new String[values.length];
        int i = 0;
        for (Description desc : values)
        {
            stringValue = desc.toString();
            stringValue = stringValue.replaceAll("_", " ");
            newDesc[i++] = stringValue;
        }
        return newDesc;
    }

    public void OpenCalendar(View view)
    {
        Calendar c = Calendar.getInstance();
        int year, month, day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        final TextView textView=(TextView) view;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth){
                        textView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }}, year, month, day);
        datePickerDialog.show();
    }

    private Long FindBIdByName(String bName, Cursor businesses)
    {
        int bNameColumnIndex = businesses.getColumnIndex(MyContract.Business.BUSINESS_NAME);
        int bIdColumnIndex = businesses.getColumnIndex(MyContract.Business.BUSINESS_ID);
        while (businesses.moveToNext())
            if (businesses.getString(bNameColumnIndex).equals(bName))
                return businesses.getLong(bIdColumnIndex);
        return (long)-1;
    }

    private long GenerateNewID(Cursor activities)
    {
        Random random = new Random();
        long newID = 0;
        boolean isNew = false;
        int idColumnIndex = activities.getColumnIndex(MyContract.Business.BUSINESS_ID);

        while (!isNew)
        {
            newID = random.nextInt(1000)+1;
            isNew = true;
            while (activities.moveToNext())
                if (activities.getLong(idColumnIndex) == newID) isNew = false;
            if (isNew) return newID;
        }
        return newID;
    }
}
