package trabelstesh.javaproject.controller;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.MyContract;

public class BusinessCursorAdapter extends CursorAdapter
{
    public BusinessCursorAdapter(Context context, Cursor c)
    {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.business_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView tvId = (TextView) view.findViewById(R.id.tvID);
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        TextView tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        TextView tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        TextView tvWebsite = (TextView) view.findViewById(R.id.tvWebsite);

        long id = cursor.getLong(cursor.getColumnIndexOrThrow(MyContract.Business.BUSINESS_ID));
        String name = cursor.getString((cursor.getColumnIndexOrThrow(MyContract.Business.BUSINESS_NAME)));
        String address = cursor.getString((cursor.getColumnIndexOrThrow(MyContract.Business.BUSINESS_ADDRESS)));
        String Phone = cursor.getString((cursor.getColumnIndexOrThrow(MyContract.Business.BUSINESS_PHONE)));
        String email = cursor.getString((cursor.getColumnIndexOrThrow(MyContract.Business.BUSINESS_EMAIL)));
        String website = cursor.getString((cursor.getColumnIndexOrThrow(MyContract.Business.BUSINESS_WEBSITE)));

        tvId.setText(String.valueOf(id));
        tvName.setText(name);
        tvAddress.setText(address);
        tvPhone.setText(Phone);
        tvEmail.setText(email);
        tvWebsite.setText(website);
    }


}
