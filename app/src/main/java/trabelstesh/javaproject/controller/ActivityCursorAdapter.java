package trabelstesh.javaproject.controller;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.MyContract;

/**
 * Created by ymsil on 1/1/2017.
 */
public class ActivityCursorAdapter extends CursorAdapter
{
    public ActivityCursorAdapter(Context context, Cursor c)
    {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.activity_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView tvAID = (TextView) view.findViewById(R.id.tvAID);
        TextView tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        TextView tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        TextView tvStartDate = (TextView) view.findViewById(R.id.tvStartDate);
        TextView tvEndDate = (TextView) view.findViewById(R.id.tvEndDate);
        TextView tvCost = (TextView) view.findViewById(R.id.tvCost);
        TextView tvShortDesc = (TextView) view.findViewById(R.id.tvShortDesc);
        TextView tvBid = (TextView) view.findViewById(R.id.tvBid);


        long id = cursor.getLong(cursor.getColumnIndexOrThrow(MyContract.Activity.ACTIVITY_ID));
        String desc = cursor.getString((cursor.getColumnIndexOrThrow(MyContract.Activity.ACTIVITY_DESCRIPTION)));
        String country = cursor.getString((cursor.getColumnIndexOrThrow(MyContract.Activity.ACTIVITY_COUNTRY)));
        String startDate = cursor.getString((cursor.getColumnIndexOrThrow(MyContract.Activity.ACTIVITY_START_DATE)));
        String endDate = cursor.getString((cursor.getColumnIndexOrThrow(MyContract.Activity.ACTIVITY_END_DATE)));
        String cost = cursor.getString((cursor.getColumnIndexOrThrow(MyContract.Activity.ACTIVITY_COST)));
        String shortDesc = cursor.getString((cursor.getColumnIndexOrThrow(MyContract.Activity.ACTIVITY_SHORT_DESCRIPTION)));
        String bId = cursor.getString((cursor.getColumnIndexOrThrow(MyContract.Activity.ACTIVITY_BUSINESS_ID)));

        tvAID.setText(String.valueOf(id));
        tvDesc.setText(desc);
        tvCountry.setText(country);
        tvStartDate.setText(startDate);
        tvEndDate.setText(endDate);
        tvCost.setText(cost);
        tvShortDesc.setText(shortDesc);
        tvBid.setText(bId);
    }
}
