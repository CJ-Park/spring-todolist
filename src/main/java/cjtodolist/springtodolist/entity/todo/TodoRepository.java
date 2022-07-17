package cjtodolist.springtodolist.entity.todo;

import cjtodolist.springtodolist.entity.todo.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findByDeadline(Pageable pageable, Deadline deadline);
    Page<Todo> findAll(Pageable pageable);
}
