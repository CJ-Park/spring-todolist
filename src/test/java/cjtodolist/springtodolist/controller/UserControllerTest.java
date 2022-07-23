package cjtodolist.springtodolist.controller;

import cjtodolist.springtodolist.DTO.UserDto;
import cjtodolist.springtodolist.entity.user.User;
import cjtodolist.springtodolist.entity.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// user 컨트롤러 통합테스트
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("회원가입 테스트")
    @Test
    public void 회원가입() throws Exception {
        //given
        Map<String, String> userDto = new HashMap<>();
        userDto.put("username", "test");
        userDto.put("password", "1234");
        userDto.put("nickname", "CJ");

        //when then
        mvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("가입 성공"));
    }

    @DisplayName("회원가입 실패 테스트 - 닉네임 미작성")
    @Test
    public void 회원가입실패_닉네임() throws Exception {
        //given
        Map<String, String> userDto = new HashMap<>();
        userDto.put("username", "test");
        userDto.put("password", "1234");

        //when then
        mvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("가입 시 nickname 은 필수입니다."));
    }

    @DisplayName("로그인 테스트")
    @Test
    public void 로그인() throws Exception {
        //given
        String username = "test";
        String password = "1234";
        String nickname = "CJ";

        userRepository.save(User.builder().username(username).nickname(nickname)
                .password(passwordEncoder.encode(password)).build());

        UserDto userDto = UserDto.builder().username(username).password(password).build();

        //when then
        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

//    @DisplayName("로그인 실패 테스트 1")
//    @Test
//    public void 로그인실패_비밀번호() throws Exception {
//        //given
//        String username = "test";
//        String password = "1234";
//        String nickname = "CJ";
//
//        String wrongPass = "4321";
//
//        userRepository.save(User.builder().username(username).nickname(nickname)
//                .password(passwordEncoder.encode(password)).build());
//
//        UserDto userDto = UserDto.builder().username(username).password(wrongPass).build();
//
//        //when then
//        mvc.perform(post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("비밀번호가 다릅니다."));
//    }
//
//    @DisplayName("로그인 실패 테스트 2")
//    @Test
//    public void 로그인실패_아이디() throws Exception {
//        //given
//        String username = "test";
//        String password = "1234";
//        String nickname = "CJ";
//
//        String wrongUsername = "justTest";
//
//        userRepository.save(User.builder().username(username).nickname(nickname)
//                .password(passwordEncoder.encode(password)).build());
//
//        UserDto userDto = UserDto.builder().username(wrongUsername).password(password).build();
//
//        //when then
//        mvc.perform(post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("존재하지 않는 username 입니다."));
//    }


//    @DisplayName("비밀번호 변경 테스트")
//    @Test
//    public void 비밀번호변경() throws Exception {
//        //given
//        String username = "test";
//        String password = "1234";
//        String nickname = "CJ";
//
//        String newPassword = "4321";
//
//        userRepository.save(User.builder().username(username).nickname(nickname)
//                .password(passwordEncoder.encode(password)).build());
//
//        UserDto userDto = UserDto.builder().username(username).password(newPassword).build();
//
//        //when then
//        mvc.perform(put("/pw-change")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("비밀번호가 변경되었습니다."));
//
//        assertThat(passwordEncoder.matches(newPassword, userRepository.findByUsername(username).get().getPassword()))
//                .isTrue();
//    }
//
//    @DisplayName("회원탈퇴 테스트")
//    @Test
//    public void 회원탈퇴() throws Exception {
//        //given
//        String username = "test";
//        String password = "1234";
//        String nickname = "CJ";
//
//        userRepository.save(User.builder().username(username).nickname(nickname)
//                .password(passwordEncoder.encode(password)).build());
//
//        UserDto userDto = UserDto.builder().username(username).password(password).build();
//
//        //when then
//        mvc.perform(delete("/quit")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("정상적으로 탈퇴 완료되었습니다."));
//
//        assertThat(userRepository.findByUsername(username)).isEmpty();
//    }

}