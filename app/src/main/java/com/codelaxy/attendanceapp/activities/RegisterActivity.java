package com.codelaxy.attendanceapp.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.service.autofill.RegexValidator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codelaxy.attendanceapp.R;
import com.codelaxy.attendanceapp.api.RetrofitClient;
import com.codelaxy.attendanceapp.models.DefaultResponse;
import com.codelaxy.attendanceapp.storage.SharedPrefManager;

import java.util.Arrays;
import java.util.regex.Pattern;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextName,editTextEmail,editTextPassword,editTextMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = (EditText)findViewById(R.id.teacherName);
        editTextEmail = (EditText)findViewById(R.id.teacherEmail);
        editTextPassword = (EditText)findViewById(R.id.teacherPwd);
        editTextMobile = (EditText) findViewById(R.id.teacherMobile);

        final Button registerBtn = (Button)findViewById(R.id.registerTeacher);
        registerBtn.setOnClickListener(this);

        String[] institutes = new String[]{"Institute 1","Institute 2","Institute 3"};
        String[] batches = new String[]{"chemistry","physics","maths"};

    }


    @Override
    public void onClick(View view) {

        if(editTextName.getText().toString().isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
        }
        if(editTextEmail.getText().toString().isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches()){
            editTextEmail.setError("Email is not valid");
            editTextEmail.requestFocus();
        }
        if(editTextPassword.getText().toString().isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
        }

        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().createTeacher(editTextName.getText().toString(),editTextEmail.getText().toString(),editTextPassword.getText().toString(),editTextMobile.getText().toString());
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Processing...");
        progressDialog.show();
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                if(response.code()==201){

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                    alertDialogBuilder.setMessage("Your request for account creation has been sent to admin. Please wait for response.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                   Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(intent);
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    progressDialog.dismiss();
                }
                else if(response.code()==422) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                    alertDialogBuilder.setMessage("Email already Exists")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    editTextEmail.requestFocus();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    progressDialog.dismiss();
                }
                else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                    alertDialogBuilder.setMessage("Failed to register")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    editTextEmail.requestFocus();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                alertDialogBuilder.setMessage("Failed to register")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editTextEmail.requestFocus();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                progressDialog.dismiss();
            }
        });
    }
}
