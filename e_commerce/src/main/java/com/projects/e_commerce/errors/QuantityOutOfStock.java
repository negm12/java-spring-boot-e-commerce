package com.projects.e_commerce.errors;

public class QuantityOutOfStock extends RuntimeException{
    public QuantityOutOfStock(String message){
        super(message);
    }
}
