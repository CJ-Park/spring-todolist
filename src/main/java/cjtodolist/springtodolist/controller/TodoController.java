package cjtodolist.springtodolist.controller;

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
        if (ObjectUtils.isEmpty(todoDto.getContent())) {
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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContent(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        todoService.updateContent(id, todoDto);

        return ResponseEntity.ok("수정 완료");
    }

    @PutMapping("{id}/ongoing")
    public ResponseEntity<String> ongoing(@PathVariable Long id) {
        todoService.ongoingById(id);

        return ResponseEntity.ok("상태 수정 - 진행중");
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<String> complete(@PathVariable Long id) {
        todoService.completeById(id);

        return ResponseEntity.ok("상태 수정 - 완료");
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
