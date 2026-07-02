package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String message){
        super(message);
    }
}
