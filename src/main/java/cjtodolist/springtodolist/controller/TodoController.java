package cjtodolist.springtodolist.controller;

import cjtodolist.springtodolist.entity.todo.Todo;
import cjtodolist.springtodolist.service.todos.TodoService;
import cjtodolist.springtodolist.DTO.TodoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todo")
@Slf4j
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoDto> create(@RequestBody TodoDto todoDto) {
        if(ObjectUtils.isEmpty(todoDto.getContent())) {
            return ResponseEntity.badRequest().build();
        }

        if(ObjectUtils.isEmpty(todoDto.getCompleted())) {
            todoDto.setCompleted(false);
        }

        Todo result = todoService.add(todoDto);
        TodoDto response = new TodoDto(result);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> readOne(@PathVariable Long id) {
        Todo result = todoService.searchById(id);
        TodoDto response = new TodoDto(result);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> readAll() {
        List<Todo> resultTodos = todoService.searchAll();
        List<TodoDto> response = resultTodos.stream()
                .map(result -> new TodoDto(result))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> update(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        Todo result = todoService.updateById(id, todoDto);
        TodoDto response = new TodoDto(result);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<TodoDto> complete(@PathVariable Long id){
        Todo result = todoService.isCompleted(id);
        TodoDto response = new TodoDto(result);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeOne(@PathVariable Long id) {
        todoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> removeAll() {
        todoService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
