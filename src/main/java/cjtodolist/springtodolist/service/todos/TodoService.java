package cjtodolist.springtodolist.service.todos;

import cjtodolist.springtodolist.entity.todo.State;
import cjtodolist.springtodolist.entity.todo.Todo;
import cjtodolist.springtodolist.entity.todo.TodoRepository;
import cjtodolist.springtodolist.DTO.TodoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// response 객체로 바꾸기 / 리턴값 확인

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

    public Todo searchById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<TodoDto> searchAll() {
        List<Todo> all = todoRepository.findAll();
        return all.stream()
                .map(result -> new TodoDto(result))
                .collect(Collectors.toList());
    }

    public TodoDto updateById(Long id, TodoDto todoDto) {
        Todo todo = searchById(id);

        todo.updateContent(todoDto.getContent());
        todo.updateState(todoDto.getState());

        return new TodoDto(todoRepository.save(todo));
    }

    public void ongoingById(Long id) {
        Todo todo = searchById(id);
        todo.updateState(State.ONGOING);
        todoRepository.save(todo);
    }

    public void completeById(Long id) {
        Todo todo = searchById(id);
        todo.updateState(State.COMPLETE);
        todoRepository.save(todo);
    }


    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }
}
