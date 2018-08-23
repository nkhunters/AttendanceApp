package com.codelaxy.attendanceapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codelaxy.attendanceapp.R;
import com.codelaxy.attendanceapp.api.RetrofitClient;
import com.codelaxy.attendanceapp.models.DefaultResponse;
import com.codelaxy.attendanceapp.models.Student;
import com.codelaxy.attendanceapp.models.StudentsResponse;
import com.codelaxy.attendanceapp.storage.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ShowDialog extends AppCompatActivity implements View.OnClickListener {

    String itemName;
    LinearLayout linearLayout,headers,layoutForP,layoutForName;
    List<Student> student;
    Button submitAttendance;
    HashMap<String,String> attendanceMap;
    HashMap<String,String> attendanceMapAbsents;
    String strInstitute,strBatch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dialog);

        attendanceMap = new HashMap<>();
        attendanceMapAbsents = new HashMap<>();

        HashMap<Integer,String> map = (HashMap<Integer, String>) getIntent().getSerializableExtra("map");
        strInstitute = map.get(1);
        strBatch = map.get(2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        submitAttendance = (Button) findViewById(R.id.submitAttendance);
        submitAttendance.setOnClickListener(this);
        headers = (LinearLayout) findViewById(R.id.headers);
        submitAttendance.setVisibility(View.GONE);
        headers.setVisibility(View.GONE);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        layoutForP = (LinearLayout) findViewById(R.id.layoutForP);
        layoutForName = (LinearLayout) findViewById(R.id.layoutForName);

        final List<Student> studentList ;

        final ProgressDialog progressDialog = new ProgressDialog(ShowDialog.this);
        progressDialog.setMessage("Loading Students...");
        progressDialog.show();

        Call<StudentsResponse> call = RetrofitClient.getInstance().getApi().getAllStudentsInBatch(strBatch,strInstitute);
        call.enqueue(new Callback<StudentsResponse>() {
            @Override
            public void onResponse(Call<StudentsResponse> call, Response<StudentsResponse> response) {
                progressDialog.dismiss();
                StudentsResponse studentsResponse = response.body();
                student = studentsResponse.getStudents();
                if(!student.isEmpty()){
                    //Toast.makeText(ShowDialog.this,student.get(0).getName(),Toast.LENGTH_LONG).show();
                    submitAttendance.setVisibility(View.VISIBLE);
                    headers.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(20,20,20,20);

                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params2.setMargins(50,20,20,20);

                    for(int i=0;i<student.size();i++){
                        final TextView rowTextView = new TextView(ShowDialog.this);
                        final TextView TextViewP = new TextView(ShowDialog.this);
                        final TextView txtname = new TextView(ShowDialog.this);
                        final String rowText = student.get(i).getRollno();
                        final String rowname = student.get(i).getName();
                        // set some properties of rowTextView or something
                        rowTextView.setText(student.get(i).getRollno());
                        rowTextView.setLayoutParams(params);
                        rowTextView.setTextAppearance(ShowDialog.this, R.style.TextAppearance_AppCompat_Medium);
                        rowTextView.setTextColor(Color.rgb(0,0,0));

                        txtname.setText(rowname);
                        txtname.setLayoutParams(params);
                        txtname.setTextAppearance(ShowDialog.this, R.style.TextAppearance_AppCompat_Medium);
                        txtname.setTextColor(Color.rgb(0,0,0));

                        TextViewP.setLayoutParams(params2);
                        TextViewP.setTextAppearance(ShowDialog.this, R.style.TextAppearance_AppCompat_Medium);
                        TextViewP.setTextColor(Color.rgb(0,0,0));


                        View v = new View(ShowDialog.this);
                        v.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                5
                        ));
                        v.setBackgroundColor(Color.parseColor("#B3B3B3"));

                        View v1 = new View(ShowDialog.this);
                        v1.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                5
                        ));
                        v1.setBackgroundColor(Color.parseColor("#B3B3B3"));

                        View v2 = new View(ShowDialog.this);
                        v2.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                5
                        ));
                        v2.setBackgroundColor(Color.parseColor("#B3B3B3"));

                        // add the textview to the linearlayout
                        linearLayout.addView(rowTextView);
                        linearLayout.addView(v);
                        layoutForName.addView(txtname);
                        layoutForName.addView(v2);
                        layoutForP.addView(TextViewP);
                        layoutForP.addView(v1);
                        rowTextView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                final CharSequence[] items = {"Present","Absent"};
                                AlertDialog.Builder builder = new AlertDialog.Builder(ShowDialog.this);
                                // builder.setTitle("Alert Dialog with ListView and Radio button");
                                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        //Toast.makeText(getApplicationContext(), rowTextView.getText().toString()+" is "+items[item], Toast.LENGTH_SHORT).show();
                                        if(items[item].toString().equals("Present"))
                                            itemName = "P";
                                        else {
                                            itemName = "A";
                                            attendanceMapAbsents.put(rowTextView.getText().toString(),txtname.getText().toString());
                                        }
                                        TextViewP.setText(itemName);
                                        dialog.dismiss();
                                        attendanceMap.put(rowTextView.getText().toString(),itemName);

                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });

                        txtname.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                final CharSequence[] items = {"Present","Absent"};
                                AlertDialog.Builder builder = new AlertDialog.Builder(ShowDialog.this);
                                // builder.setTitle("Alert Dialog with ListView and Radio button");
                                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        //Toast.makeText(getApplicationContext(), rowTextView.getText().toString()+" is "+items[item], Toast.LENGTH_SHORT).show();
                                        if(items[item].toString().equals("Present"))
                                            itemName = "P";
                                        else{
                                            itemName = "A";
                                            attendanceMapAbsents.put(rowTextView.getText().toString(),txtname.getText().toString());
                                        }
                                        TextViewP.setText(itemName);
                                        attendanceMap.put(rowTextView.getText().toString(),itemName);
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });

                    }
                }
                else{
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(ShowDialog.this);
                    alertDialogBuilder.setMessage("No students in this batch")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    Intent intent = new Intent(ShowDialog.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<StudentsResponse> call, Throwable t) {
                Toast.makeText(ShowDialog.this,"Failed",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
                Intent intent = new Intent(ShowDialog.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        Iterator it = attendanceMap.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            Log.v("hashmap",pair.getKey() + " = " + pair.getValue());

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .insertAttendance(pair.getKey().toString(), formattedDate, pair.getValue().toString(), strBatch, strInstitute);

        final ProgressDialog progressDialog = new ProgressDialog(ShowDialog.this);
        progressDialog.setMessage("Uploading Attendance...");
        progressDialog.show();
        call.enqueue(new Callback<DefaultResponse>() {
            @Override

            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                DefaultResponse dr = response.body();
                if(response.code() == 201) {

                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(ShowDialog.this);
                    alertDialogBuilder.setMessage("Attendance Submitted Please check the absentees.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    Intent intent = new Intent(ShowDialog.this, VerifyAttendance.class);
                                    intent.putExtra("attendanceMapAbsents",attendanceMapAbsents);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            });
                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else{
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(ShowDialog.this);
                    alertDialogBuilder.setMessage("Attendance already marked")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    Intent intent = new Intent(ShowDialog.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            });
                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

                Toast.makeText(ShowDialog.this,t.getMessage(),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}

    @Override
    protected void onStart() {
        super.onStart();
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(ShowDialog.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}