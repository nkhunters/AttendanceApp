package com.codelaxy.attendanceapp.activities;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.codelaxy.attendanceapp.models.DefaultResponse;
import com.codelaxy.attendanceapp.R;
import com.codelaxy.attendanceapp.api.RetrofitClient;
import com.codelaxy.attendanceapp.models.LoginResponse;
import com.codelaxy.attendanceapp.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAdmin extends AppCompatActivity {

    EditText Username, Password;
    Button LogIn ;
    public static String strUname,strPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        Username = (EditText)findViewById(R.id.username);
        Password = (EditText)findViewById(R.id.password);
        LogIn = (Button)findViewById(R.id.loginAdmin);



        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strUname = Username.getText().toString();
                strPwd = Password.getText().toString();
                if(strUname.isEmpty()){
                    Username.setError("Username is required");
                    Username.requestFocus();
                    return;
                }

                if(strPwd.isEmpty()){
                    Password.setError("Password is required");
                    Password.requestFocus();
                    return;
                }
                if(strUname.equals("nkhunters") && strPwd.equals("qwerty")){
                    SharedPrefManager.getInstance(LoginAdmin.this).saveUser(strUname);
                    Intent intent = new Intent(LoginAdmin.this,DashboardAdmin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                Call<LoginResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .adminLogin(strUname, strPwd);

                final ProgressDialog progressDialog = new ProgressDialog(LoginAdmin.this);
                progressDialog.setMessage("Loging In...");
                progressDialog.show();
                call.enqueue(new Callback<LoginResponse>() {
                    @Override

                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        LoginResponse dr = response.body();
                        if(response.code()==201) {

                            //Toast.makeText(LoginAdmin.this,dr.getMessage(),Toast.LENGTH_LONG).show();
                            SharedPrefManager.getInstance(LoginAdmin.this).saveUser(strUname);
                            Intent intent = new Intent(LoginAdmin.this,DashboardAdmin.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else{

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginAdmin.this);
                            alertDialogBuilder.setMessage("Invalid username or password")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                            progressDialog.dismiss();
                        }

                        progressDialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                        Toast.makeText(LoginAdmin.this,t.getMessage(),Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });

            }}
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(LoginAdmin.this,DashboardAdmin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else {

        }
    }
}

