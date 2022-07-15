package cjtodolist.springtodolist.DTO;

import cjtodolist.springtodolist.entity.todo.Deadline;
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
    private Deadline deadline;
    private State state;

    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.content = todo.getContent();
        this.deadline = todo.getDeadline();
        this.state = todo.getState();
    }
}
