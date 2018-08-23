package com.codelaxy.attendanceapp.models;

import android.util.Log;

import com.codelaxy.attendanceapp.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetInstitute {

    public static List<String> getInstitute(){

        final List<String> instList = new ArrayList<>();
        Call<InstituteResponse> call = RetrofitClient.getInstance().getApi().getInstitutes();
        call.enqueue(new Callback<InstituteResponse>() {
            @Override
            public void onResponse(Call<InstituteResponse> call, Response<InstituteResponse> response) {
                List<Institute> list = response.body().getInstitutes();
                for(Institute institute : list){
                    String inst = institute.getName();
                    instList.add(inst);
                    Log.v("institutes",inst);
                }
            }

            @Override
            public void onFailure(Call<InstituteResponse> call, Throwable t) {

            }
        });

        return instList;
    }
}
