package cjtodolist.springtodolist.service.todos;

import cjtodolist.springtodolist.entity.todo.State;
import cjtodolist.springtodolist.entity.todo.Todo;
import cjtodolist.springtodolist.entity.todo.TodoRepository;
import cjtodolist.springtodolist.DTO.TodoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public void add(TodoDto todoDto) {
        Todo todo = Todo.builder()
                .content(todoDto.getContent())
                .state(State.READY)
                .build();
        todoRepository.save(todo);
    }

    public TodoDto searchTodo(Long id) {
        Todo findTodo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 Todo 입니다.");
                });
        return new TodoDto(findTodo);
    }

    public List<TodoDto> searchAll() {
        List<Todo> all = todoRepository.findAll();
        return all.stream()
                .map(result -> new TodoDto(result))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateContent(Long id, TodoDto todoDto) {
        Todo findTodo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 Todo 입니다.");
                });
        findTodo.updateContent(todoDto.getContent());
    }

    @Transactional
    public void ongoingById(Long id) {
        Todo findTodo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 Todo 입니다.");
                });
        findTodo.updateState(State.ONGOING);
    }

    @Transactional
    public void completeById(Long id) {
        Todo findTodo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 Todo 입니다.");
                });
        findTodo.updateState(State.COMPLETE);
    }


    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }
}
