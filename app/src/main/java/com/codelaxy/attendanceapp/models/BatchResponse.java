package com.codelaxy.attendanceapp.models;

import java.util.List;

public class BatchResponse {

    private boolean error;
    private List<Batch> batches;

    public BatchResponse(boolean error, List<Batch> batches) {
        this.error = error;
        this.batches = batches;
    }

    public boolean isError() {
        return error;
    }

    public List<Batch> getBatches() {
        return batches;
    }
}
