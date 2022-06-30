package cjtodolist.springtodolist.controller;

import cjtodolist.springtodolist.config.JwtTokenProvider;
import cjtodolist.springtodolist.entity.user.Admin;
import cjtodolist.springtodolist.entity.user.User;
import cjtodolist.springtodolist.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/join")
    public String join(@RequestBody Map<String, String> user) {
        User saveUser = User.builder()
                .username(user.get("username"))
                .password(user.get("password"))
                .admin(Admin.USER)
                .nickname(user.get("nickname"))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        userRepository.save(saveUser);
        log.info("회원가입 완료");
        return saveUser.getUsername();
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        log.info("username = {}", user.get("username"));
        User user1 = userRepository.findByUsername(user.get("username"))
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 USERNAME 입니다"));
        if(!user.get("password").equals(user1.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(user1.getUsername(), user1.getRoles());
    }
}