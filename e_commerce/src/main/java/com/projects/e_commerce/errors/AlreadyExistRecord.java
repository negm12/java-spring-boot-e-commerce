package com.projects.e_commerce.errors;

public class AlreadyExistRecord extends RuntimeException {
    public AlreadyExistRecord(String message) {
        super(message);
    }
}
