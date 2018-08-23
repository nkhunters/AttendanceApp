package com.codelaxy.attendanceapp.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.codelaxy.attendanceapp.R;
import com.codelaxy.attendanceapp.models.GetBatch;
import com.codelaxy.attendanceapp.models.GetInstitute;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class EnquiryHistory extends AppCompatActivity implements View.OnClickListener {

    EditText institute,course,enquiryDate;
    Button checkHistory;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialogEnquiryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkHistory = (Button)findViewById(R.id.checkHistory);
        checkHistory.setOnClickListener(this);
        institute = (EditText)findViewById(R.id.institute);
        course = (EditText)findViewById(R.id.course);
        enquiryDate = (EditText)findViewById(R.id.enquiryDate);
        enquiryDate.setOnClickListener(this);
        final List<String> institutes = GetInstitute.getInstitute();
        final List<String> batches = (List<String>) GetBatch.getBatch();

        institute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = institutes.toArray(new CharSequence[institutes.size()]);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EnquiryHistory.this);
                // builder.setTitle("Alert Dialog with ListView and Radio button");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        //Toast.makeText(getApplicationContext(), rowTextView.getText().toString()+" is "+items[item], Toast.LENGTH_SHORT).show();
                        institute.setText(items[item]);
                        institute.setInputType(InputType.TYPE_NULL);
                        dialog.dismiss();
                    }
                });

                android.app.AlertDialog alert = builder.create();
                alert.show();
            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialogEnquiryDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                enquiryDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        DatePickerDialogEnquiryDate.getDatePicker().setMinDate(System.currentTimeMillis()-1000);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == enquiryDate){
            DatePickerDialogEnquiryDate.show();
        }
        if(view == checkHistory)
        {
            if(institute.getText().toString().isEmpty()){
                institute.setError("Branch is required");
                institute.requestFocus();
                return;
            }
            if(course.getText().toString().isEmpty()){
                course.setError("Course is required");
                course.requestFocus();
                return;
            }

            HashMap<Integer,String> map = new HashMap();
            map.put(1,institute.getText().toString());
            map.put(2,course.getText().toString());
            map.put(3,enquiryDate.getText().toString());

            Intent intent = new Intent(EnquiryHistory.this,ShowEnquiryHistory.class);
            intent.putExtra("map",map);
            startActivity(intent);
        }
    }
}
