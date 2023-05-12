package com.kavitha.restapi.controller;

import com.kavitha.restapi.exception.UserTodoCollectionException;
import com.kavitha.restapi.model.Todo;
import com.kavitha.restapi.repository.TodoRespository;
import com.kavitha.restapi.service.UserTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private TodoRespository todoRepo;
    @Autowired
    private UserTodoService userTodoService;

    //read alltodo
    @GetMapping("/todos")
    public ResponseEntity<?> getAll() {
        List<Todo> todos = userTodoService.getAll();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    // create newtodo
    @PostMapping(value = "/todos")
    public ResponseEntity<?> createTodo(@RequestBody Todo todo) {
        try {
            userTodoService.createTodo(todo);
            return new ResponseEntity<Todo>(todo, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserTodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //read by id
    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(userTodoService.getSingleTodo(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //updatetodo
    @PatchMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody Todo todo) {
        try {
            userTodoService.updateTodo(id, todo);
            return new ResponseEntity<>("Updated user todo with id  (" + id + ") is successful", HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserTodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // deletetodo by id
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            userTodoService.deleteTodoById(id);
            return new ResponseEntity<>("Deleted user successfully with id " + id, HttpStatus.OK);
        } catch (UserTodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
