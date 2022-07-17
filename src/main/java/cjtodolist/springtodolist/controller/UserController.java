package cjtodolist.springtodolist.controller;

import cjtodolist.springtodolist.DTO.TodoDto;
import cjtodolist.springtodolist.DTO.UserJoinDto;
import cjtodolist.springtodolist.DTO.UserDto;
import cjtodolist.springtodolist.service.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 투두 조회
    @GetMapping("/{id}")
    public Page<TodoDto> userTodo(Pageable pageable, @PathVariable Long id) {
        return userService.getTodos(pageable, id);
    }

    // 비밀번호 수정
    @PutMapping("/pw-change")
    public ResponseEntity<String> changePw(@RequestBody UserDto userDto) {
        userService.update(userDto);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    // 회원 탈퇴
    @DeleteMapping("/quit")
    public ResponseEntity<String> quit(@RequestBody UserDto userDto) {
        userService.delete(userDto);
        return ResponseEntity.ok("정상적으로 탈퇴 완료되었습니다.");
    }
}