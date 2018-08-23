package com.codelaxy.attendanceapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.codelaxy.attendanceapp.R;

import java.util.HashMap;
import java.util.Iterator;


public class VerifyAttendance extends AppCompatActivity implements View.OnClickListener {

    HashMap<String,String> attendanceMapAbsents;
    LinearLayout layoutRollNo,layoutName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button okBtn = (Button)findViewById(R.id.okBtn);
        okBtn.setOnClickListener(this);
        attendanceMapAbsents = (HashMap) getIntent().getSerializableExtra("attendanceMapAbsents");
        layoutRollNo = (LinearLayout) findViewById(R.id.layoutRollNo);
        layoutName = (LinearLayout)findViewById(R.id.layoutName);
        if(attendanceMapAbsents.isEmpty()){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VerifyAttendance.this);
            alertDialogBuilder.setMessage("All Students are present today")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            Intent intent = new Intent(VerifyAttendance.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else
            printMap(attendanceMapAbsents);
    }

    public void printMap(HashMap mp) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(50,20,20,20);
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            Log.v("hashmap",pair.getKey() + " = " + pair.getValue());
            final TextView rowTextView = new TextView(VerifyAttendance.this);
            rowTextView.setText(pair.getKey().toString());
            rowTextView.setLayoutParams(params);
            rowTextView.setTextAppearance(VerifyAttendance.this, R.style.TextAppearance_AppCompat_Medium);
            rowTextView.setTextColor(Color.rgb(0,0,0));
            layoutRollNo.addView(rowTextView);

            final TextView rowTextView2 = new TextView(VerifyAttendance.this);
            rowTextView2.setText(pair.getValue().toString());
            rowTextView2.setLayoutParams(params);
            rowTextView2.setTextAppearance(VerifyAttendance.this, R.style.TextAppearance_AppCompat_Medium);
            rowTextView2.setTextColor(Color.rgb(0,0,0));
            layoutName.addView(rowTextView2);

            View v = new View(VerifyAttendance.this);
            v.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    5
            ));
            v.setBackgroundColor(Color.parseColor("#B3B3B3"));

            View v1 = new View(VerifyAttendance.this);
            v1.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    5
            ));
            v1.setBackgroundColor(Color.parseColor("#B3B3B3"));
            layoutRollNo.addView(v);
            layoutName.addView(v1);
            it.remove(); // avoids a ConcurrentModificationException
        }}

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(VerifyAttendance.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
