package com.codelaxy.attendanceapp.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.codelaxy.attendanceapp.R;
import com.codelaxy.attendanceapp.api.RetrofitClient;
import com.codelaxy.attendanceapp.models.DefaultResponse;
import com.codelaxy.attendanceapp.models.GetBatch;
import com.codelaxy.attendanceapp.models.GetInstitute;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Enquiry extends AppCompatActivity implements View.OnClickListener {


    private DatePickerDialog DatePickerDialogEnquiryDate;
    private DatePickerDialog DatePickerDialogJoiningDate;
    private SimpleDateFormat dateFormatter;

    EditText institute,studName,mobile,batchName,Enqbatch,joinDate,enquiryDate,remarks;
    Button button;
    String strinstName,strstudName,strmobile,strbatchName,strremarks,strEnqDate,strJoinDate ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        institute = (EditText)findViewById(R.id.institute);
        Enqbatch = (EditText)findViewById(R.id.Enqbatch);
        studName = (EditText)findViewById(R.id.studentName);
        mobile = (EditText)findViewById(R.id.mobile);
        remarks = (EditText)findViewById(R.id.remarks);
        button = (Button) findViewById(R.id.submitEnquiry);
        final List<String> institutes = GetInstitute.getInstitute();
        final List<String> batches = (List<String>) GetBatch.getBatch();

        institute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = institutes.toArray(new CharSequence[institutes.size()]);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Enquiry.this);
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

        Enqbatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = batches.toArray(new CharSequence[batches.size()]);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Enquiry.this);
                // builder.setTitle("Alert Dialog with ListView and Radio button");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        //Toast.makeText(getApplicationContext(), rowTextView.getText().toString()+" is "+items[item], Toast.LENGTH_SHORT).show();
                        Enqbatch.setText(items[item]);
                        Enqbatch.setInputType(InputType.TYPE_NULL);
                        dialog.dismiss();
                    }
                });

                android.app.AlertDialog alert = builder.create();
                alert.show();
            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();
        button.setOnClickListener(this);

    }

    private void insertData() {

        strinstName = institute.getText().toString();
        strbatchName = Enqbatch.getText().toString();
        strstudName = studName.getText().toString().trim();
        strmobile = mobile.getText().toString().trim();
        strremarks = remarks.getText().toString().trim();
        strEnqDate = enquiryDate.getText().toString();
        strJoinDate = joinDate.getText().toString();

        if (strstudName.isEmpty()) {
            studName.setError("Student name is required");
            studName.requestFocus();
            return;
        }
        if (strmobile.isEmpty()) {
            mobile.setError("Mobile no. is required");
            mobile.requestFocus();
            return;
        }
        if (strmobile.length() != 10) {

            mobile.setError("Not Valid Mobile No.");
            mobile.requestFocus();
            return;
        }

        if (strremarks.isEmpty()) {
            remarks.setError("Remarks are required");
            remarks.requestFocus();
            return;
        }
        if (strEnqDate.isEmpty()) {
            enquiryDate.setError("Student name is required");
            enquiryDate.requestFocus();
            return;
        }
        if (strJoinDate.isEmpty()) {
            joinDate.setError("Student name is required");
            joinDate.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .insertEnquiry(strinstName, strstudName, strmobile, strbatchName, strremarks, strEnqDate, strJoinDate);

        final ProgressDialog progressDialog = new ProgressDialog(Enquiry.this);
        progressDialog.setMessage("Inserting Enquiry");
        progressDialog.show();
        call.enqueue(new Callback<DefaultResponse>() {
            @Override

            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                    if(response.code() == 201) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Enquiry.this);
                        alertDialogBuilder.setMessage("Enquiry Inserted")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        Intent intent = new Intent(Enquiry.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        progressDialog.dismiss();
                    }
                    else{

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Enquiry.this);
                        alertDialogBuilder.setMessage("Some Error occured. Please try again")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        Intent intent = new Intent(Enquiry.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        progressDialog.dismiss();
                    }

                    progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Enquiry.this);
                alertDialogBuilder.setMessage("Some Error occured. Please try again")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                Intent intent = new Intent(Enquiry.this,MainActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                progressDialog.dismiss();
            }

        });
    }

    private void findViewsById() {
        joinDate = (EditText) findViewById(R.id.joiningDate);
        joinDate.setInputType(InputType.TYPE_NULL);

        enquiryDate = (EditText)findViewById(R.id.enquiryDate);
        enquiryDate.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {

        joinDate.setOnClickListener(this);
        enquiryDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialogEnquiryDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                enquiryDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialogJoiningDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                joinDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialogJoiningDate.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        DatePickerDialogEnquiryDate.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == joinDate){
            DatePickerDialogJoiningDate.show();
        }
        else if(view == enquiryDate){
            DatePickerDialogEnquiryDate.show();
        }
        if(view == button)
        {
            insertData();
        }
    }
}
