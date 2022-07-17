package cjtodolist.springtodolist.controller;

import cjtodolist.springtodolist.DTO.TodoDeadlineDto;
import cjtodolist.springtodolist.DTO.TodoStateDto;
import cjtodolist.springtodolist.entity.todo.Deadline;
import cjtodolist.springtodolist.service.todos.TodoService;
import cjtodolist.springtodolist.DTO.TodoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todo")
@Slf4j
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody TodoDto todoDto) {
        if (ObjectUtils.isEmpty(todoDto.getContent()) || ObjectUtils.isEmpty(todoDto.getDeadline())) {
            return ResponseEntity.badRequest().build();
        }

        todoService.add(todoDto);

        return ResponseEntity.ok("저장 완료!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> readOne(@PathVariable Long id) {
        TodoDto response = todoService.searchTodo(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public Page<TodoDto> readAll(Pageable pageable) {
        return todoService.getTodoList(pageable);
    }

    @GetMapping("/deadline")
    public Page<TodoDto> readByDeadline(Pageable pageable, @RequestBody TodoDeadlineDto dto) {
        return todoService.searchByDeadline(pageable, dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContent(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        todoService.updateTodo(id, todoDto);

        return ResponseEntity.ok("content / deadline 수정");
    }

    @PutMapping("{id}/state")
    public ResponseEntity<String> updateState(@PathVariable Long id, @RequestBody TodoStateDto dto) {
        todoService.updateState(id, dto);

        return ResponseEntity.ok("State 수정");
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
