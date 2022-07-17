package cjtodolist.springtodolist.DTO;

import cjtodolist.springtodolist.entity.todo.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoStateDto {
    private State state;
}
