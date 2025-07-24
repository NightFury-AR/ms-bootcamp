package io.github.nightfuryar.springwebsecuritydemo.util;


import io.github.nightfuryar.springwebsecuritydemo.dto.TodoDTO;
import io.github.nightfuryar.springwebsecuritydemo.entity.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public TodoDTO toDTO(Todo entity) {
        return TodoDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .isCompleted(entity.isCompleted())
                .build();
    }

    public Todo toEntity(TodoDTO dto,String userId) {
        Todo entity = new Todo();
        entity.setTitle(dto.getTitle());
        entity.setCompleted(dto.isCompleted());
        entity.setUserId(userId);
        return entity;
    }
}
