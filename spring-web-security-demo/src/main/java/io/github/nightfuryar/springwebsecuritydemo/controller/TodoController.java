package io.github.nightfuryar.springwebsecuritydemo.controller;

import io.github.nightfuryar.springwebsecuritydemo.dto.TodoDTO;
import io.github.nightfuryar.springwebsecuritydemo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
public class TodoController {

    @Autowired private final TodoService todoService;


    @GetMapping
    @PreAuthorize("hasAuthority('READ_PRIVILEGES')")
    public List<TodoDTO> allTodos() {
        return todoService.findAllTodo();
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGES')")
    public TodoDTO createTodo(@RequestBody TodoDTO todo) {
        return todoService.createNew(todo);
    }


    @PutMapping("/complete/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGES')")
    public TodoDTO complete(@PathVariable("id") String todoId) {
        return todoService.completeToDo(todoId);
    }
}