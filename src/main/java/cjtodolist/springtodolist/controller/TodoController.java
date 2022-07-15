package cjtodolist.springtodolist.controller;

import cjtodolist.springtodolist.entity.todo.Deadline;
import cjtodolist.springtodolist.service.todos.TodoService;
import cjtodolist.springtodolist.DTO.TodoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<List<TodoDto>> readAll() {
        List<TodoDto> response = todoService.searchAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/daily")
    public ResponseEntity<List<TodoDto>> readDaily() {
        List<TodoDto> response = todoService.searchDaily();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<TodoDto>> readWeekly() {
        List<TodoDto> response = todoService.searchWeekly();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<TodoDto>> readMonthly() {
        List<TodoDto> response = todoService.searchMonthly();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContent(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        todoService.updateTodo(id, todoDto);

        return ResponseEntity.ok("content / deadline 수정");
    }

    @PutMapping("{id}/ready")
    public ResponseEntity<String> ready(@PathVariable Long id) {
        todoService.readyById(id);

        return ResponseEntity.ok("State 수정 - ONGOING");
    }

    @PutMapping("{id}/ongoing")
    public ResponseEntity<String> ongoing(@PathVariable Long id) {
        todoService.ongoingById(id);

        return ResponseEntity.ok("State 수정 - ONGOING");
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<String> complete(@PathVariable Long id) {
        todoService.completeById(id);

        return ResponseEntity.ok("State 수정 - COMPLETE");
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
