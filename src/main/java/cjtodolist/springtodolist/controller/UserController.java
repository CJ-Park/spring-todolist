package cjtodolist.springtodolist.controller;

import cjtodolist.springtodolist.DTO.UserJoinDto;
import cjtodolist.springtodolist.DTO.UserDto;
import cjtodolist.springtodolist.service.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinDto userJoinDto) {
        if (ObjectUtils.isEmpty(userJoinDto.getUsername())) {
            return new ResponseEntity<>("가입 시 username 은 필수입니다.", HttpStatus.BAD_REQUEST);
        }
        if (ObjectUtils.isEmpty(userJoinDto.getPassword())) {
            return new ResponseEntity<>("가입 시 password 는 필수입니다.", HttpStatus.BAD_REQUEST);
        }
        if (ObjectUtils.isEmpty(userJoinDto.getNickname())) {
            return new ResponseEntity<>("가입 시 nickname 은 필수입니다.", HttpStatus.BAD_REQUEST);
        }

        if (userService.isDuplicateUsername(userJoinDto)) {
            return new ResponseEntity<>("이미 존재하는 username 입니다.", HttpStatus.BAD_REQUEST);
        }

        userService.add(userJoinDto);
        return new ResponseEntity<>("가입 성공", HttpStatus.OK);

    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        if (userService.validateUsername(userDto)) {
            if (userService.validateUserPass(userDto)) {
                String token = userService.getToken(userDto);
                return ResponseEntity.ok(token);
            } else {
                return new ResponseEntity<>("비밀번호가 다릅니다.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("존재하지 않는 username 입니다.", HttpStatus.BAD_REQUEST);
        }
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