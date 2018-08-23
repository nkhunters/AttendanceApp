package com.codelaxy.attendanceapp.models;

import java.util.List;

public class InstituteResponse {

    private boolean error;
    private List<Institute> institutes;

    public InstituteResponse(boolean error, List<Institute> institutes) {
        this.error = error;
        this.institutes = institutes;
    }

    public boolean isError() {
        return error;
    }

    public List<Institute> getInstitutes() {
        return institutes;
    }
}
