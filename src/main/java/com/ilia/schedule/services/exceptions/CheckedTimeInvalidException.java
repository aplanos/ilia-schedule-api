package com.ilia.schedule.services.exceptions;

public class CheckedTimeInvalidException extends RuntimeException {

    public CheckedTimeInvalidException(String message){
        super(message);
    }
}
