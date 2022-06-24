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

    final String NAME = "king";
    final String PASSWORD = "1234";
    final String NICKNAME = "덩도";
    final Long ID = 1L;
    final Admin ADMIN = Admin.USER;

    User user = User.builder()
            .username(NAME)
            .password(PASSWORD)
            .admin(ADMIN)
            .id(ID)
            .nickname(NICKNAME)
            .roles(Collections.singletonList("ROLE_USER"))
            .build();

    @PostMapping("/join")
    public String join() {
        userRepository.save(user);
        log.info("회원가입 완료");
        return user.toString();
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        log.info("username = {}", user.get("username"));
        User user1 = userRepository.findByUsername(user.get("username"))
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 USERNAME 입니다"));

        return jwtTokenProvider.createToken(user1.getUsername(), user1.getRoles());
    }
}