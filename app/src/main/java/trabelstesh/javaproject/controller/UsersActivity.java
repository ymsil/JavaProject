package trabelstesh.javaproject.controller;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.MyContract;
import trabelstesh.javaproject.model.entities.User;

public class UsersActivity extends AppCompatActivity
{

    ArrayList<User> allUsers;
    SharedPreferences sharedPreferences;
    public static final String NAME_KEY = "nameKey";
    public static final String PASSWORD_KEY = "passwordKey";
    boolean isChecked;
    long randomID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        EditText userNameEditText = (EditText) findViewById(R.id.userName);
        EditText userPasswordEditText = (EditText) findViewById(R.id.userPassword);
        allUsers = new ArrayList<>();
        isChecked = true;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.contains(NAME_KEY))
            userNameEditText.setText(sharedPreferences.getString(NAME_KEY, null));
        if (sharedPreferences.contains(PASSWORD_KEY))
            userPasswordEditText.setText(sharedPreferences.getString(PASSWORD_KEY, null));


        new AsyncTask< Void, Void, ArrayList<User>>()
        {
            @Override
            protected ArrayList<User> doInBackground(Void... params)
            {
                Cursor users = getContentResolver().query(MyContract.User.USER_URI, null, null, null, null);
                User user;
                while (users.moveToNext())
                {
                    user = new User();
                    user.setId(users.getLong(users.getColumnIndex(MyContract.User.USER_ID)));
                    user.setName(users.getString(users.getColumnIndex(MyContract.User.USER_NAME)));
                    user.setPassword(users.getString(users.getColumnIndex(MyContract.User.USER_PASSWORD)));
                    allUsers.add(user);
                }
                users.close();
                return allUsers;
            }
        }.execute();
    }

    public void OnSignInClick(View view)
    {
        EditText userNameEditText = (EditText) findViewById(R.id.userName);
        EditText userPasswordEditText = (EditText) findViewById(R.id.userPassword);

        String name = userNameEditText.getText().toString();
        String password = userPasswordEditText.getText().toString();
        for (User user : allUsers)
        {
            if (user.getName().equals(name))
            {
                if (user.getPassword().equals(password))
                {
                    Toast.makeText(getApplicationContext(), "login successful",
                            Toast.LENGTH_SHORT).show();
                    if (isChecked) SaveToSharedPreferences(user);
                    Intent sigInIntent = new Intent(this, MenuActivity.class);
                    startActivity(sigInIntent);
                    break;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "wrong password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void OnRegisterClick(View view)
    {
        EditText userNameEditText = (EditText) findViewById(R.id.userName);
        EditText userPasswordEditText = (EditText) findViewById(R.id.userPassword);

        final String name = userNameEditText.getText().toString();
        String password = userPasswordEditText.getText().toString();
        boolean nameFlag = false;
        for (User user : allUsers)
        {
            if (user.getName().equals(name))
            {
                Toast.makeText(getApplicationContext(), "user name exists. choose a different name",
                        Toast.LENGTH_SHORT).show();
                nameFlag = true;
                break;
            }
        }
        if (!nameFlag)
        {
            randomID = GenerateNewID(allUsers);
            if (randomID == -1)
            {
                Toast.makeText(getApplicationContext(), "problem generating new ID",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
            User newUser = new User(randomID, name, password);
            final ContentValues cv = new ContentValues();
            cv.put(MyContract.User.USER_ID, randomID);
            cv.put(MyContract.User.USER_NAME, name);
            cv.put(MyContract.User.USER_PASSWORD, password);
            new AsyncTask<Void, Void, Uri>() {
                @Override
                protected Uri doInBackground(Void... params)
                {
                    Uri uri = getContentResolver().insert(MyContract.User.USER_URI, cv);
//                    long id = ContentUris.parseId(uri);

                    return uri;
                }
            }.execute();
            Toast.makeText(getApplicationContext(), "Welcome " + name + ". new user registered",
                    Toast.LENGTH_SHORT).show();

            if (isChecked) SaveToSharedPreferences(newUser);

            Intent regintent = new Intent(UsersActivity.this, MenuActivity.class);
            startActivity((regintent));
        }

    }

    private long GenerateNewID(ArrayList<User> allUsers)
    {
        Random random = new Random();
        long newID;
        boolean isNew = false;

        while (!isNew)
        {
            newID = random.nextInt(1000) + 1;
            isNew = true;
            for (User u : allUsers) if (u.getId() == newID) isNew = false;
            if (isNew) return newID;
        }
        return -1;

    }

    private void SaveToSharedPreferences(User user)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME_KEY, user.getName());
        editor.putString(PASSWORD_KEY, user.getPassword());
        editor.commit();
    }
    public void Unchecked(View view)
    {
        isChecked = !isChecked;
        if (!isChecked)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }
    }

    //Todo: add UI for updating and deleting users
}
