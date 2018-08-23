package com.codelaxy.attendanceapp.api;

import com.codelaxy.attendanceapp.models.BatchResponse;
import com.codelaxy.attendanceapp.models.DefaultResponse;
import com.codelaxy.attendanceapp.models.InstituteResponse;
import com.codelaxy.attendanceapp.models.LoginResponse;
import com.codelaxy.attendanceapp.models.StudentsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("getAllStudentsInBatch")
    Call<StudentsResponse> getAllStudentsInBatch(@Field("batch") String batch,
                                                 @Field("institute") String institute);

    @GET("getInstitutes")
    Call<InstituteResponse> getInstitutes();

    @GET("getBatchesInInstitute")
    Call<BatchResponse> getBatchesInInstitute();

    @FormUrlEncoded
    @POST("loginTeacher")
    Call<DefaultResponse> loginTeacher(@Field("email") String email,
                                       @Field("password") String password);

    @FormUrlEncoded
    @POST("createStudent")
    Call<DefaultResponse> createStudent(@Field("name") String name,
                                        @Field("rollno") String rollno,
                                        @Field("batch") String batch,
                                        @Field("institute") String institute);

    @FormUrlEncoded
    @POST("createAdmission")
    Call<DefaultResponse> createAdmission(@Field("institute") String institute,
                                          @Field("name") String name,
                                          @Field("mobile") String mobile,
                                          @Field("admissiondate") String admissiondate,
                                          @Field("course") String course,
                                          @Field("coursefee") String coursefee,
                                          @Field("paidamt") String paidamt,
                                          @Field("dueamt") String dueamt,
                                          @Field("duedate") String duedate,
                                          @Field("batch") String batch,
                                          @Field("remarks") String remarks);

    @FormUrlEncoded
    @POST("createTeacher")
    Call<DefaultResponse> createTeacher(@Field("name") String name,
                                        @Field("email") String email,
                                        @Field("password") String password,
                                        @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("insertAttendance")
    Call<DefaultResponse> insertAttendance(@Field("RollNo") String RollNo,
                                           @Field("AttendDate") String AttendDate,
                                           @Field("status") String status,
                                           @Field("batch") String batch,
                                           @Field("institute") String institute);

    @FormUrlEncoded
    @POST("insertEnquiry")
    Call<DefaultResponse> insertEnquiry(@Field("instname") String instname,
                                        @Field("studname") String studname,
                                        @Field("mobile") String mobile,
                                        @Field("batchname") String batchname,
                                        @Field("remarks") String remarks,
                                        @Field("enqdate") String  enqdate,
                                        @Field("joindate") String joindate);

    @FormUrlEncoded
    @POST("adminLogin")
    Call<LoginResponse> adminLogin(@Field("username") String username,
                                            @Field("password") String password);

}
