package cjtodolist.springtodolist.controller;

import cjtodolist.springtodolist.DTO.UserJoinDto;
import cjtodolist.springtodolist.DTO.UserLoginDto;
import cjtodolist.springtodolist.config.JwtTokenProvider;
import cjtodolist.springtodolist.entity.user.User;
import cjtodolist.springtodolist.service.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity<String> join (@RequestBody UserJoinDto userJoinDto) {
        if(ObjectUtils.isEmpty(userJoinDto.getUsername())){
            return new ResponseEntity<>("가입 시 username 은 필수입니다.", HttpStatus.BAD_REQUEST);
        }
        if(ObjectUtils.isEmpty(userJoinDto.getPassword())){
            return new ResponseEntity<>("가입 시 password 는 필수입니다.", HttpStatus.BAD_REQUEST);
        }
        if(ObjectUtils.isEmpty(userJoinDto.getNickname())){
            return new ResponseEntity<>("가입 시 nickname 은 필수입니다.", HttpStatus.BAD_REQUEST);
        }

        if(userService.isDuplicateUsername(userJoinDto)) {
            return new ResponseEntity<>("이미 존재하는 username 입니다", HttpStatus.BAD_REQUEST);
        }

        String nickname = userService.add(userJoinDto).getNickname();
        return new ResponseEntity<>(nickname + " 님 가입 성공", HttpStatus.OK);

    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody UserLoginDto userLoginDto) {
        User findUser = userService.validateUser(userLoginDto);
        String token = jwtTokenProvider.createToken(findUser.getUsername(), findUser.getRoles());
        return ResponseEntity.ok(token);
    }
}