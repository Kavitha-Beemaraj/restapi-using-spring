package com.kavitha.restapi.exception;

public class UserTodoCollectionException extends Exception {


    public UserTodoCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return " User with id(" + id + ") not found";
    }

    public static String UserAlreadyExists() {

        return "User with given name already exists";
    }
}
