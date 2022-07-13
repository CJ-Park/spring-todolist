package cjtodolist.springtodolist.service.users;

import cjtodolist.springtodolist.DTO.UserJoinDto;
import cjtodolist.springtodolist.DTO.UserDto;
import cjtodolist.springtodolist.config.JwtTokenProvider;
import cjtodolist.springtodolist.entity.user.User;
import cjtodolist.springtodolist.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// 에러 커스텀하기
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void add(UserJoinDto userJoinDto) {
        User user = User.builder()
                .username(userJoinDto.getUsername())
                .password(passwordEncoder.encode(userJoinDto.getPassword()))
                .nickname(userJoinDto.getNickname())
                .build();

        isDuplicatedUser(userJoinDto);

        userRepository.save(user);
    }

    public String validateUser(UserDto userDto) {
        User findUser = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 ID 입니다.");
                });
        if (!passwordEncoder.matches(userDto.getPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(findUser.getUsername(), findUser.getRoles());
    }

    public void isDuplicatedUser(UserJoinDto userJoinDto) {
        if (userRepository.findByUsername(userJoinDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 ID 입니다.");
        }
    }

    public void update(UserDto userDto) {
        User updateUser = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 ID 입니다.");
                });
        updateUser.changePw(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(updateUser);
    }

    public void delete(UserDto userDto) {
        User deleteUser = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 ID 입니다.");
                });
        if (!passwordEncoder.matches(userDto.getPassword(), deleteUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        userRepository.delete(deleteUser);
    }
}
