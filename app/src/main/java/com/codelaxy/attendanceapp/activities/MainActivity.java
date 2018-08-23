package com.codelaxy.attendanceapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codelaxy.attendanceapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button adminButton,attendanceButton,enquiryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adminButton = (Button)findViewById(R.id.adminButton);
        attendanceButton = (Button)findViewById(R.id.attendanceButton);
        enquiryButton = (Button)findViewById(R.id.enquiryButton);
        adminButton.setOnClickListener(this);
        attendanceButton.setOnClickListener(this);
        enquiryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == adminButton){
            Intent intent = new Intent(MainActivity.this,DashboardAdmin.class);
            startActivity(intent);
        }
        if(view == attendanceButton){
            Intent intent = new Intent(MainActivity.this,Attendance.class);
            startActivity(intent);
        }
        if(view == enquiryButton){
            Intent intent = new Intent(MainActivity.this,Enquiry.class);
            startActivity(intent);
        }
    }
}
