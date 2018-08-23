package com.codelaxy.attendanceapp.models;

import com.codelaxy.attendanceapp.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBatch {

    public static List<String> getBatch(){

        final List<String> batchList = new ArrayList<>();
        Call<BatchResponse> call = RetrofitClient.getInstance().getApi().getBatchesInInstitute();
        call.enqueue(new Callback<BatchResponse>() {
            @Override
            public void onResponse(Call<BatchResponse> call, Response<BatchResponse> response) {
                List<Batch> list = response.body().getBatches();
                for(Batch batch : list){
                    String inst = batch.getName();
                    batchList.add(inst);
                }
            }

            @Override
            public void onFailure(Call<BatchResponse> call, Throwable t) {

            }
        });

        return batchList;
    }
}
