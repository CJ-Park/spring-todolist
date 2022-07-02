package cjtodolist.springtodolist.controller;

import cjtodolist.springtodolist.DTO.UserDto;
import cjtodolist.springtodolist.DTO.UserJoinDto;
import cjtodolist.springtodolist.config.JwtTokenProvider;
import cjtodolist.springtodolist.entity.user.User;
import cjtodolist.springtodolist.entity.user.UserRepository;
import cjtodolist.springtodolist.service.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

// user 통합테스트
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
    private JwtTokenProvider jwtTokenProvider;


    @Test
    public void 회원가입테스트() throws Exception {
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
        assertThat(findAllUser.get(0).getPassword()).isEqualTo(password);
    }

    // 테스트 실패 -> 존재하지 않는 ID 예외 처리
    @Test
    public void 로그인테스트() throws Exception {
        //given
        userRepository.save(User.builder().username("test1").password("1234")
                .nickname("TEST").build());
        UserDto userDto = UserDto.builder().username("test1").password("1234").build();
        User loginUser = userRepository.findByUsername(userDto.getUsername())
                .filter(user1 -> user1.getPassword().equals(userDto.getPassword()))
                .orElseThrow(()->{throw new IllegalArgumentException("잘못된 password");});

        String token = jwtTokenProvider.createToken(loginUser.getUsername(), loginUser.getRoles());

        String url = "http://localhost:" + port + "/login";

        //when
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(url, userDto, String.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(token);

    }

}