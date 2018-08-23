package com.codelaxy.attendanceapp.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

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

public class Admission extends AppCompatActivity implements View.OnClickListener {

    private DatePickerDialog DatePickerDialogAdmissionDate;
    private DatePickerDialog DatePickerDialogDueDate;
    private SimpleDateFormat dateFormatter;

    EditText studentName,mobile,admissionDate,course,courseFee,paidAmt,dueAmt,dueDate,remarks,institute,batch;
    Button submitAdmission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);
        submitAdmission = (Button)findViewById(R.id.submitAddmission);
        submitAdmission.setOnClickListener(this);

        studentName = (EditText)findViewById(R.id.studentName);
        mobile = (EditText) findViewById(R.id.mobile);
        admissionDate = (EditText)findViewById(R.id.admissionDate);
        admissionDate.setInputType(InputType.TYPE_NULL);
        course = (EditText)findViewById(R.id.course);
        courseFee = (EditText)findViewById(R.id.courseFee);
        paidAmt = (EditText)findViewById(R.id.paidAmt);
        dueAmt = (EditText)findViewById(R.id.dueAmt);
        dueDate = (EditText)findViewById(R.id.dueDate);
        dueDate.setInputType(InputType.TYPE_NULL);
        remarks = (EditText)findViewById(R.id.remarks);
        institute = (EditText) findViewById(R.id.institute);
        batch = (EditText) findViewById(R.id.batch);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        final List<String> institutes = GetInstitute.getInstitute();
        final List<String> batches = (List<String>) GetBatch.getBatch();

        institute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = institutes.toArray(new CharSequence[institutes.size()]);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Admission.this);
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

        batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = batches.toArray(new CharSequence[batches.size()]);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Admission.this);
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

    private void setDateTimeField() {

        admissionDate.setOnClickListener(this);
        dueDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialogAdmissionDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                admissionDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialogDueDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dueDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialogAdmissionDate.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        DatePickerDialogDueDate.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {

        if(view == admissionDate){
            DatePickerDialogAdmissionDate.show();
        }
        else if(view == dueDate){
            DatePickerDialogDueDate.show();
        }
        if(view == submitAdmission){

            String strInstitute = institute.getText().toString();
            String strBatch = batch.getText().toString();
            String strStudentName = studentName.getText().toString();
            String strMobile = mobile.getText().toString();
            String strAdmissionDate = admissionDate.getText().toString();
            String strCourse = course.getText().toString();
            String strCourseFee = courseFee.getText().toString();
            String strPaidAmt = paidAmt.getText().toString();
            String strDueAmt = dueAmt.getText().toString();
            String strDueDate = dueDate.getText().toString();
            String strRemarks = remarks.getText().toString();

            Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().createAdmission(strInstitute,strStudentName,strMobile,strAdmissionDate,strCourse,strCourseFee,strPaidAmt,strCourseFee,strDueDate,strBatch,strRemarks);
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait...");
            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if(response.code() == 201){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Admission.this);
                        alertDialogBuilder.setMessage("Admission Successful")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        Intent intent = new Intent(Admission.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        progressDialog.dismiss();
                    }
                    else {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Admission.this);
                            alertDialogBuilder.setMessage("Admission Unsuccessful please try again")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            Intent intent = new Intent(Admission.this,MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                            progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {

                }
            });
        }
    }
}
