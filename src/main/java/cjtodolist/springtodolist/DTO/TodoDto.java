package cjtodolist.springtodolist.DTO;

import cjtodolist.springtodolist.entity.todo.State;
import cjtodolist.springtodolist.entity.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private Long id;
    private String content;
    private State state;

    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.content = todo.getContent();
        this.state = todo.getState();
    }
}
