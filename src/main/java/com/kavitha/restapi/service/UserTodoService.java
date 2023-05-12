package com.kavitha.restapi.service;

import com.kavitha.restapi.exception.UserTodoCollectionException;
import com.kavitha.restapi.model.Todo;
import javax.validation.ConstraintViolationException;
import java.util.List;

public interface UserTodoService {
    public void createTodo(Todo todo) throws ConstraintViolationException, UserTodoCollectionException;

    public List<Todo> getAll();

    public Todo getSingleTodo(String id) throws UserTodoCollectionException;

    public void updateTodo(String id, Todo todo) throws UserTodoCollectionException;

    public void deleteTodoById(String id) throws UserTodoCollectionException;
}
