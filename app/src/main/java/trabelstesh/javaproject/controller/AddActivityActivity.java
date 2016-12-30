package trabelstesh.javaproject.controller;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import trabelstesh.javaproject.R;

public class AddActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
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

    public void AddActivity(View view)
    {
    }

    public void Back(View view) {
    }
}
