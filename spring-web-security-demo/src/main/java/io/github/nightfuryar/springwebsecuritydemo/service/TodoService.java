package io.github.nightfuryar.springwebsecuritydemo.service;

import io.github.nightfuryar.springwebsecuritydemo.auth.repository.AppUserRepository;
import io.github.nightfuryar.springwebsecuritydemo.dto.TodoDTO;
import io.github.nightfuryar.springwebsecuritydemo.entity.Todo;
import io.github.nightfuryar.springwebsecuritydemo.repository.TodoRepository;
import io.github.nightfuryar.springwebsecuritydemo.util.TodoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service @Slf4j
public class TodoService {

    @Autowired private AppUserRepository appUserRepository;
    @Autowired private TodoRepository repository;
    @Autowired private TodoMapper todoMapper;

    public List<TodoDTO> findAllTodo() {
        String userId = this.getUserIdByUserName();
        return repository
                .findByUserId(userId)
                .stream()
                .map(todoMapper::toDTO)
                .toList();
    }

    public TodoDTO createNew(TodoDTO newTodo) {
        String currentUserId = this.getUserIdByUserName();
        Todo entity = todoMapper.toEntity(newTodo, currentUserId);
        Todo savedEntity = repository.save(entity);
        return todoMapper.toDTO(savedEntity);
    }

    public TodoDTO completeToDo(String id) {
        Todo existingTodo = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("no todo found for this id" + id));
        existingTodo.setCompleted(true);
        Todo updated = repository.save(existingTodo);
        return todoMapper.toDTO(updated);
    }

    private String getUserIdByUserName() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username))
                .getId()
                .toString();
    }

}
