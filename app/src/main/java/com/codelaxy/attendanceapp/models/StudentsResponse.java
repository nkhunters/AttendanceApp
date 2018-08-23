package com.codelaxy.attendanceapp.models;

import java.util.List;

public class StudentsResponse {

    private boolean error;
    private List<Student> students;

    public StudentsResponse(boolean error, List<Student> students) {
        this.error = error;
        this.students = students;
    }

    public boolean isError() {
        return error;
    }

    public List<Student> getStudents() {
        return students;
    }
}
