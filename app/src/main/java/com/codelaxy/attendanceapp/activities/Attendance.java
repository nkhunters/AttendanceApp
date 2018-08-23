package com.codelaxy.attendanceapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.codelaxy.attendanceapp.R;
import com.codelaxy.attendanceapp.models.GetBatch;
import com.codelaxy.attendanceapp.models.GetInstitute;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

public class Attendance extends AppCompatActivity implements View.OnClickListener {

    EditText institute,batch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Button attendBtn = (Button)findViewById(R.id.attendBtn);
        attendBtn.setOnClickListener(this);
        batch = (EditText) findViewById(R.id.batch);
        institute = (EditText) findViewById(R.id.attendInst);
        final List<String> institutes = GetInstitute.getInstitute();
        final List<String> batches = (List<String>) GetBatch.getBatch();

        institute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = institutes.toArray(new CharSequence[institutes.size()]);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Attendance.this);
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        institute.setText(items[item]);
                        institute.setInputType(InputType.TYPE_NULL);
                        dialog.dismiss();
                    }
                });

                android.app.AlertDialog alert = builder.create();
                alert.show();
            }
        });

        batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = batches.toArray(new CharSequence[batches.size()]);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Attendance.this);
                // builder.setTitle("Alert Dialog with ListView and Radio button");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        //Toast.makeText(getApplicationContext(), rowTextView.getText().toString()+" is "+items[item], Toast.LENGTH_SHORT).show();
                        batch.setText(items[item]);
                        batch.setInputType(InputType.TYPE_NULL);
                        dialog.dismiss();
                    }
                });

                android.app.AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        String inst = institute.getText().toString();
        String strbatch = institute.getText().toString();
        HashMap<Integer,String> map = new HashMap<>();
        map.put(1,inst);
        map.put(2,strbatch);
        Intent intent = new Intent(Attendance.this,ShowDialog.class);
        intent.putExtra("map",map);
        startActivity(intent);
    }
}
