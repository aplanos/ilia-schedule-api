package com.ilia.schedule.services.exceptions;

public class CheckedTimeExistException extends RuntimeException {

    public CheckedTimeExistException(){
        super("Horários já registrado");
    }
}
