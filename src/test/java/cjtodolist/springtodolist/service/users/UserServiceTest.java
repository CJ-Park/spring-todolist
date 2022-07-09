package cjtodolist.springtodolist.service.users;

import cjtodolist.springtodolist.DTO.UserDto;
import cjtodolist.springtodolist.DTO.UserJoinDto;
import cjtodolist.springtodolist.entity.user.User;
import cjtodolist.springtodolist.entity.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;

// 검증 서비스 시 userRepository 에서 확인할 값을 가져와야됨 => 목객체 반환값 임의 설정??
@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Spy
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원가입 서비스 테스트")
    @Test
    public void 회원가입_서비스() throws Exception {
        //given
        String username = "test";
        String password = "1234";
        String nickname = "CJ";

        UserJoinDto userJoinDto = UserJoinDto.builder().username(username).password(password).nickname(nickname).build();

        //when
        userService.add(userJoinDto);

        //then
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(any(String.class));

    }

    @DisplayName("회원 아이디 중복 여부 테스트")
    @Test
    public void 회원중복_검증() throws Exception {
        //given
        String username = "test";
        String password = "1234";
        String nickname = "CJ";

        UserJoinDto userJoinDto = UserJoinDto.builder().username(username).password(password).nickname(nickname).build();

        //when
        userService.isDuplicateUsername(userJoinDto);

        //then
        verify(userRepository, times(1)).findByUsername(any(String.class));
    }

    @DisplayName("로그인 Username 검증")
    @Test
    public void 로그인검증_username() throws Exception {
        //given
        String username = "test";
        String password = "1234";

        UserDto userDto = UserDto.builder().username(username).password(password).build();

        //when
        userService.validateUsername(userDto);

        //then
        verify(userRepository, times(1)).findByUsername(any(String.class));
    }

    // ============= java.util.NoSuchElementException at UserServiceTest.java:83 =============
    @DisplayName("로그인 Userpass 검증")
    @Test
    public void 로그인검증_password() throws Exception {
        //given
        String username = "test";
        String password = "1234";

        UserDto userDto = UserDto.builder().username(username).password(password).build();

        //when
        userService.validateUserPass(userDto);

        //then
        verify(userRepository, times(1)).findByUsername(any(String.class));
        verify(passwordEncoder, times(1)).matches(any(CharSequence.class), any(String.class));
    }

}