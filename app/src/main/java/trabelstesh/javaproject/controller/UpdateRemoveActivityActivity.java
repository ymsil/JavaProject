package trabelstesh.javaproject.controller;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.MyContract;
import trabelstesh.javaproject.model.entities.Activity;
import trabelstesh.javaproject.model.entities.Description;

public class UpdateRemoveActivityActivity extends AppCompatActivity {

    long aId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_remove);

        final Spinner descriptionSpinner =(Spinner) findViewById(R.id.descriptionUpdateSpinner);
        String[] Desc = DescriptionWithSpaces(Description.values());
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Desc);
        descriptionSpinner.setAdapter(arrayAdapter);
        final EditText countryText = (EditText)findViewById(R.id.countryUpdateEditText);
        final TextView startDateText = (TextView)findViewById(R.id.startDateUpdateText);
        final TextView endDateText = (TextView) findViewById(R.id.endDateUpdateText);
        final EditText costText = (EditText)findViewById(R.id.CostUpdateText);
        final EditText shortDescText = (EditText)findViewById(R.id.shortDescUpdateText);

        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params) {
                return getContentResolver().query(MyContract.Business.BUSINESS_URI, new String[]{},"",new String[]{},"");
            }

            @Override
            protected void onPostExecute(Cursor allBusinesses) {
                super.onPostExecute(allBusinesses);

                final int businessNameColumnIndex = allBusinesses.getColumnIndex(MyContract.Business.BUSINESS_NAME);
                int i = 0;
                String [] businessNames = new String[allBusinesses.getCount()];
                while (allBusinesses.moveToNext())
                {
                    businessNames[i++] = allBusinesses.getString(businessNameColumnIndex);
                }
                final Spinner bIdSpinner = (Spinner)findViewById(R.id.businessIdUpdateSpinner);
                final ArrayAdapter businessArrayAdapter = new ArrayAdapter<>(UpdateRemoveActivityActivity.this, android.R.layout.simple_spinner_item, businessNames);
                bIdSpinner.setAdapter(businessArrayAdapter);

                Bundle getRowFromActivity = getIntent().getExtras();

                aId = -1;
                if (getRowFromActivity != null) aId = getRowFromActivity.getLong(MyContract.Activity.ACTIVITY_ID);

                new AsyncTask<Void, Void, Cursor>() {
                    @Override
                    protected Cursor doInBackground(Void... params) {
                        return getContentResolver().query(MyContract.Activity.ACTIVITY_URI, new String[]{},"",new String[]{},"");
                    }

                    @Override
                    protected void onPostExecute(Cursor allActivities) {
                        super.onPostExecute(allActivities);

                        while(allActivities.moveToNext())
                        {
                            if (allActivities.getLong(allActivities.getColumnIndex(MyContract.Activity.ACTIVITY_ID)) == aId)
                            {
                                String oldDescription = allActivities.getString(allActivities.getColumnIndex(MyContract.Activity.ACTIVITY_DESCRIPTION));
                                descriptionSpinner.setSelection(arrayAdapter.getPosition(oldDescription));
                                countryText.setText(allActivities.getString(allActivities.getColumnIndex(MyContract.Activity.ACTIVITY_COUNTRY)));
                                startDateText.setText(allActivities.getString(allActivities.getColumnIndex(MyContract.Activity.ACTIVITY_START_DATE)));
                                endDateText.setText(allActivities.getString(allActivities.getColumnIndex(MyContract.Activity.ACTIVITY_END_DATE)));
                                costText.setText(allActivities.getString(allActivities.getColumnIndex(MyContract.Activity.ACTIVITY_COST)));
                                shortDescText.setText(allActivities.getString(allActivities.getColumnIndex(MyContract.Activity.ACTIVITY_SHORT_DESCRIPTION)));
                                final long oldBID = allActivities.getLong(allActivities.getColumnIndex(MyContract.Activity.ACTIVITY_BUSINESS_ID));

                                new AsyncTask<Void, Void, Cursor>() {
                                    @Override
                                    protected Cursor doInBackground(Void... params) {
                                        return getContentResolver().query(MyContract.Business.BUSINESS_URI, new String[]{},"",new String[]{},"");
                                    }

                                    @Override
                                    protected void onPostExecute(Cursor allBusinesses) {
                                        super.onPostExecute(allBusinesses);
                                        String oldBIDname = null;
                                        while (allBusinesses.moveToNext())
                                        {
                                            if (allBusinesses.getLong(allBusinesses.getColumnIndex(MyContract.Business.BUSINESS_ID)) == oldBID)
                                                oldBIDname = allBusinesses.getString(businessNameColumnIndex);
                                        }
                                        bIdSpinner.setSelection(businessArrayAdapter.getPosition(oldBIDname));
                                    }
                                }.execute();
                            }
                        }
                    }
                }.execute();
            }
        }.execute();
    }

    public void UpdateActivity(View view) throws Exception {
        Spinner descriptionSpinner =(Spinner) findViewById(R.id.descriptionUpdateSpinner);
        String description = descriptionSpinner.getSelectedItem().toString().replaceAll(" ", "_");
        EditText countryText = (EditText)findViewById(R.id.countryUpdateEditText);

        TextView startDateText = (TextView)findViewById(R.id.startDateUpdateText);
        String startDate = startDateText.getText().toString();
        TextView endDateText = (TextView) findViewById(R.id.endDateUpdateText);
        String endDate = endDateText.getText().toString();
        EditText costText = (EditText)findViewById(R.id.CostUpdateText);
        EditText shortDescText = (EditText)findViewById(R.id.shortDescUpdateText);
        Spinner bIdSpinner = (Spinner)findViewById(R.id.businessIdUpdateSpinner);
        String bName = bIdSpinner.getSelectedItem().toString();
        Cursor allBusinesses = getContentResolver().query(MyContract.Business.BUSINESS_URI, new String[]{},"",new String[]{},"");
        Long bId = FindBIdByName(bName, allBusinesses);
        if (bId < 0) throw new Exception("Problem with business ID");

        Activity updatedActivity = new Activity();
        updatedActivity.setId(aId);
        updatedActivity.setDescription(description);
        updatedActivity.setCountry(countryText.getText().toString());
        updatedActivity.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(startDate));
        updatedActivity.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(endDate));
        updatedActivity.setCost(Integer.parseInt(costText.getText().toString()));
        updatedActivity.setShortDescription(shortDescText.getText().toString());
        updatedActivity.setBusinessId(bId);

        ContentValues cv = new ContentValues();
        cv.put(MyContract.Activity.ACTIVITY_ID, updatedActivity.getId());
        cv.put(MyContract.Activity.ACTIVITY_DESCRIPTION, updatedActivity.getDescription().toString());
        cv.put(MyContract.Activity.ACTIVITY_COUNTRY, updatedActivity.getCountry());
        cv.put(MyContract.Activity.ACTIVITY_START_DATE, new SimpleDateFormat("dd/MM/yyyy").format(updatedActivity.getStartDate()));
        cv.put(MyContract.Activity.ACTIVITY_END_DATE, new SimpleDateFormat("dd/MM/yyyy").format(updatedActivity.getEndDate()));
        cv.put(MyContract.Activity.ACTIVITY_COST, updatedActivity.getCost());
        cv.put(MyContract.Activity.ACTIVITY_SHORT_DESCRIPTION, updatedActivity.getShortDescription());
        cv.put(MyContract.Activity.ACTIVITY_BUSINESS_ID, updatedActivity.getBusinessId());
        getContentResolver().update(MyContract.Activity.ACTIVITY_URI, cv, "_id = ?", new String[]{String.valueOf(aId)});
        Toast.makeText(getApplicationContext(), "activity updated", Toast.LENGTH_SHORT).show();
        this.finish();
    }
    public void DeleteActivity(View view)
    {
        getContentResolver().delete(MyContract.Activity.ACTIVITY_URI,"_id = ?", new String[]{String.valueOf(aId)});
        Toast.makeText(getApplicationContext(), "activity deleted", Toast.LENGTH_SHORT).show();
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

    private Long FindBIdByName(String bName, Cursor businesses)
    {
        int bNameColumnIndex = businesses.getColumnIndex(MyContract.Business.BUSINESS_NAME);
        int bIdColumnIndex = businesses.getColumnIndex(MyContract.Business.BUSINESS_ID);
        while (businesses.moveToNext())
            if (businesses.getString(bNameColumnIndex) == bName)
                return businesses.getLong(bIdColumnIndex);
        return (long)-1;
    }

    public void OpenUpdateCalendar(View view)
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


}
