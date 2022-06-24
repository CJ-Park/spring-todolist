package cjtodolist.springtodolist.service.todos;

import cjtodolist.springtodolist.entity.todo.Todo;
import cjtodolist.springtodolist.entity.todo.TodoRepository;
import cjtodolist.springtodolist.DTO.TodoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public Todo add(TodoDto todoDto) {
        Todo todo = Todo.builder()
                .content(todoDto.getContent())
                .completed(todoDto.getCompleted())
                .build();
        return todoRepository.save(todo);
    }

    public Todo searchById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Todo> searchAll() {
        return todoRepository.findAll();
    }

    public Todo updateById(Long id, TodoDto todoDto) {
        Todo todo = searchById(id);

        if(todoDto.getContent() != null){
            todo.updateContent(todoDto.getContent());
        }

        if(todoDto.getCompleted() != null){
            todo.updateComplete(todoDto.getCompleted());
        }

        return todoRepository.save(todo);
    }

    public Todo isCompleted(Long id) {
        Todo todo = searchById(id);
        if(todo.getCompleted() == false){
            todo.isCompleted();
        }
        return todoRepository.save(todo);
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }
}
