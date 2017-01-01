package trabelstesh.javaproject.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.DBManagerFactory;
import trabelstesh.javaproject.model.backend.IDB_manager;
import trabelstesh.javaproject.model.backend.MyContract;
import trabelstesh.javaproject.model.datasource.Tools;
import trabelstesh.javaproject.model.entities.User;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity
{
    SharedPreferences sharedPreferences;
    TextView nameTextView;
    TextView passwordTextView;
    public static final String NAME_KEY = "nameKey";
    public static final String PASSWORD_KEY = "passwordKey";
    boolean isChecked;


    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) delayedHide(AUTO_HIDE_DELAY_MILLIS);
            return false;        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isChecked = true;
        nameTextView = (TextView) findViewById(R.id.nametxt);
        passwordTextView = (TextView) findViewById(R.id.passwordtxt);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.contains(NAME_KEY))
            nameTextView.setText(sharedPreferences.getString(NAME_KEY, null));
        if (sharedPreferences.contains(PASSWORD_KEY))
            passwordTextView.setText(sharedPreferences.getString(PASSWORD_KEY, null));

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }
    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }
    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }
    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }
    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void SignRegisterClick(View view)
    {
        User user = new User();
        String name = nameTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        user.setName(name);
        user.setPassword(password);

        IDB_manager dbm = DBManagerFactory.getManager();
        Cursor users = dbm.GetAllUsers();
        while (users.moveToNext())
        {
            if (((User) users).getName() == name)
                if (((User) users).getPassword() == password)
                {
                    Toast.makeText(getApplicationContext(), "login successful",
                            Toast.LENGTH_SHORT).show();
                    if (isChecked) SaveToSharedPreferences(user);
                    //Intent regintent = new Intent(this, MenuActivity.class);
                    Intent regintent = new Intent(this, TroubleActivity.class);
                    startActivity((regintent));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "wrong password",
                            Toast.LENGTH_SHORT).show();
                }
        }
        long randomID = GenerateNewID(users);
        user.setId(randomID);
        ContentValues cv = new ContentValues();
        cv.put(MyContract.User.USER_ID, user.getId());
        cv.put(MyContract.User.USER_NAME, user.getName());
        cv.put(MyContract.User.USER_PASSWORD, user.getPassword());
        dbm.AddUser(cv);

        Toast.makeText(getApplicationContext(), "Welcome " + user.getName() + ". new user registered",
                Toast.LENGTH_SHORT).show();
//        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coo)
//        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Welcome " + user.getName() + ". new user registered", Snackbar.LENGTH_LONG).show();
        if (isChecked) SaveToSharedPreferences(user);
        //Intent regintent = new Intent(this, MenuActivity.class);
        Intent regintent = new Intent(this, TroubleActivity.class);
        startActivity((regintent));
    }

    private void SaveToSharedPreferences(User user)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = sharedPreferences.edit();
        editor.putString(NAME_KEY, user.getName());
        editor.putString(PASSWORD_KEY, user.getPassword());
        editor.commit();
    }

    private long GenerateNewID(Cursor users)
    {
        Random random = new Random();
        long newID = 0;
        boolean isNew = false;

        while (!isNew)
        {
            newID = random.nextInt(1000)+1;
            isNew = true;
            while (users.moveToNext())
                if ( ((User)users).getId() == newID ) isNew = false;
            if (isNew) return newID;
        }
        return newID;
    }

    public void Unchecked(View view)
    {
        isChecked = !isChecked;
        if (!isChecked)
        {
            Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }
    }
}