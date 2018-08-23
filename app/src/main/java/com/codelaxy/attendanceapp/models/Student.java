package com.codelaxy.attendanceapp.models;

public class Student {

    private String rollno;
    private String name,batch;

    public Student(String rollno, String name, String batch) {
        this.rollno = rollno;
        this.name = name;
        this.batch = batch;
    }

    public String getRollno() {
        return rollno;
    }

    public String getName() {
        return name;
    }

    public String getBatch() {
        return batch;
    }
}
