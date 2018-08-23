package com.codelaxy.attendanceapp.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codelaxy.attendanceapp.R;
import com.codelaxy.attendanceapp.api.RetrofitClient;
import com.codelaxy.attendanceapp.models.GetBatch;
import com.codelaxy.attendanceapp.models.GetInstitute;
import com.codelaxy.attendanceapp.models.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStudent extends AppCompatActivity implements View.OnClickListener {

    EditText institute,studName,studRollno,batch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Button addStudent = (Button) findViewById(R.id.addStudent);
        addStudent.setOnClickListener(this);
        institute = (EditText) findViewById(R.id.institute);
        studName = (EditText) findViewById(R.id.studName);
        studRollno = (EditText) findViewById(R.id.studRollno);
        batch = (EditText) findViewById(R.id.batch);
        final List<String> institutes = GetInstitute.getInstitute();
        final List<String> batches = (List<String>) GetBatch.getBatch();

        institute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = institutes.toArray(new CharSequence[institutes.size()]);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddStudent.this);
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
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddStudent.this);
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
        String strName,strRollNo,strBatch,strInstitute;
        strName = studName.getText().toString();
        strRollNo = studRollno.getText().toString();
        strBatch = batch.getText().toString();
        strInstitute = institute.getText().toString();

        Call call = RetrofitClient.getInstance().getApi().createStudent(strName,strRollNo,strBatch,strInstitute);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == 201){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddStudent.this);
                    alertDialogBuilder.setMessage("Student Added")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    Intent intent = new Intent(AddStudent.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    progressDialog.dismiss();
                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddStudent.this);
                    alertDialogBuilder.setMessage("Student not added. Please try again")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    Intent intent = new Intent(AddStudent.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }
}
