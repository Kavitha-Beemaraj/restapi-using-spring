package com.kavitha.restapi.service;

import com.kavitha.restapi.exception.UserTodoCollectionException;
import com.kavitha.restapi.model.Todo;
import com.kavitha.restapi.repository.TodoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserTodoServiceImpl implements UserTodoService {

    @Autowired
    private TodoRespository todoRepo;

    @Override
    public void createTodo(Todo todo) throws ConstraintViolationException, UserTodoCollectionException {
        Optional<Todo> todoOptional = todoRepo.findbyName(todo.getName());
        if (todoOptional.isPresent()) {
            throw new UserTodoCollectionException(UserTodoCollectionException.UserAlreadyExists());
        } else {
            //todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
        }
    }

    @Override
    public List<Todo> getAll() {
        List<Todo> todos = todoRepo.findAll();
        if (todos.size() > 0) {
            return todos;
        } else {
            return new ArrayList<Todo>();
        }
    }

    @Override
    public Todo getSingleTodo(String id) throws UserTodoCollectionException {
        Optional<Todo> todoOptional = todoRepo.findById(id);
        if (!todoOptional.isPresent()) {
            throw new UserTodoCollectionException(UserTodoCollectionException.NotFoundException(id));
        } else {
            return todoOptional.get();
        }
    }

    @Override
    public void updateTodo(String id, Todo todo) throws UserTodoCollectionException {
        Optional<Todo> todoOptional = todoRepo.findById(id);
        Optional<Todo> todoWithSameName = todoRepo.findbyName(todo.getName());
        if (todoOptional.isPresent()) {

            if (todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)) {
                throw new UserTodoCollectionException(UserTodoCollectionException.UserAlreadyExists());
            }
            todo.setId(todoOptional.get().getId());
            todoRepo.save(todo);
        } else {
            throw new UserTodoCollectionException(UserTodoCollectionException.NotFoundException(id));
        }

    }

    @Override
    public void deleteTodoById(String id) throws UserTodoCollectionException {
        Optional<Todo> todoOptional = todoRepo.findById(id);
        if (!todoOptional.isPresent()) {
            throw new UserTodoCollectionException(UserTodoCollectionException.NotFoundException(id));
        } else {
            todoRepo.deleteById(id);
        }
    }
}
