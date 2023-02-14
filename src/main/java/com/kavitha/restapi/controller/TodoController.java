package com.kavitha.restapi.controller;

import com.kavitha.restapi.model.Todo;
import com.kavitha.restapi.repository.TodoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {
    @Autowired
    private TodoRespository todoRepo;

    //read alltodo
    @GetMapping("/todos")
    public ResponseEntity<?> getAll(){
        List<Todo> todos = todoRepo.findAll();
        if (todos.size() > 0){
            return new ResponseEntity<List<Todo>>(todos, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("no todos available", HttpStatus.NOT_FOUND);
        }
    }

    // create newtodo
    @PostMapping(value = "/todos")
    public ResponseEntity<?> createTodo(@RequestBody Todo todo){
        try {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
            return new ResponseEntity<Todo>(todo, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //read by id
    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id){
        Optional<Todo> todoOptional = todoRepo.findById(id);
        if(todoOptional.isPresent()){
            return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("no todo with id" +id, HttpStatus.NOT_FOUND);
        }
    }

    //updatetodo
    @PatchMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody Todo todo){
        Optional<Todo> todoOptional = todoRepo.findById(id);
        if(todoOptional.isPresent()){
            Todo todoSave = todoOptional.get();
            todoSave.setCompleted(todo.isCompleted() ? todo.isCompleted(): todoSave.isCompleted());
            todoSave.setTodo(todo.getTodo()!= null? todo.getTodo() : todoSave.getTodo() );
            todoRepo.save(todoSave);
            return new ResponseEntity<>(todoSave, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("no todo with id" +id, HttpStatus.NOT_FOUND);
        }
    }

    // deletetodo by id
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
        try {
            todoRepo.deleteById(id);
            return new ResponseEntity<>("deleted successfully with id" + id, HttpStatus.OK);
        }catch ( Exception e){
            return new ResponseEntity<> (e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
