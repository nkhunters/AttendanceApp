package com.codelaxy.attendanceapp.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.codelaxy.attendanceapp.R;
import com.codelaxy.attendanceapp.models.User;
import com.codelaxy.attendanceapp.storage.SharedPrefManager;

public class DashboardAdmin extends AppCompatActivity{

    TextView txtWelcome;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
       txtWelcome.setText(SharedPrefManager.getInstance(this).getUser());
        //SharedPrefManager.getInstance(this).clear();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(DashboardAdmin.this,LoginAdmin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

   }
