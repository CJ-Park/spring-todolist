package cjtodolist.springtodolist.controller;

import cjtodolist.springtodolist.DTO.UserDto;
import cjtodolist.springtodolist.DTO.UserJoinDto;
import cjtodolist.springtodolist.config.JwtTokenProvider;
import cjtodolist.springtodolist.entity.user.User;
import cjtodolist.springtodolist.entity.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// user 컨트롤러 통합테스트
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class UserControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Test
    public void 회원가입테스트() {
        //given
        String username = "king01286";
        String password = "1234";
        String nickname = "CJ";

        UserJoinDto userJoinDto = UserJoinDto.builder().username(username).nickname(nickname)
                .password(password).build();

        String url = "http://localhost:" + port + "/join";

        //when
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(url, userJoinDto, String.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(nickname + " 님 가입 성공");

        List<User> findAllUser = userRepository.findAll();
        System.out.println(findAllUser.get(0).getNickname());
        assertThat(findAllUser.get(0).getUsername()).isEqualTo(username);
        assertThat(passwordEncoder.matches(password, findAllUser.get(0).getPassword())).isTrue();
    }

    // 로그인 테스트 계속 에러남
    @Test
    public void 로그인테스트() throws Exception {
        //given
        String username = "king01286";
        String password = "1234";
        String nickname = "CJ";

        User joinUser = User.builder().username(username).nickname(nickname)
                .password(passwordEncoder.encode(password)).build();

        UserDto loginUser = UserDto.builder().username(username).password(password).build();

        userRepository.save(joinUser);

        String url = "http://localhost:" + port + "/login";

        //when
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(url, loginUser, String.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}