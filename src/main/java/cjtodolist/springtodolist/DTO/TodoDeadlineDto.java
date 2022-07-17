package cjtodolist.springtodolist.DTO;

import cjtodolist.springtodolist.entity.todo.Deadline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDeadlineDto {
    private Deadline deadline;
}
