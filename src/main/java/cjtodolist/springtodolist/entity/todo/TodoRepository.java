package cjtodolist.springtodolist.entity.todo;

import cjtodolist.springtodolist.entity.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
