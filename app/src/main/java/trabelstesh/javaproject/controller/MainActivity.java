package trabelstesh.javaproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import trabelstesh.javaproject.R;
import trabelstesh.javaproject.model.backend.CheckerService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, CheckerService.class);
        startService(intent);
    }


    public void EnterClick(View view) {
        Intent regintent = new Intent(this, LoginActivity.class);
        startActivity((regintent));
    }
}
