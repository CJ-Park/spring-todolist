package cjtodolist.springtodolist.entity.todo;

import cjtodolist.springtodolist.entity.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByDeadline(Deadline deadline);
}
