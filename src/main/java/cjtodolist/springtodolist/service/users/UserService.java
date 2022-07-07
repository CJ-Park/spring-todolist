package cjtodolist.springtodolist.service.users;

import cjtodolist.springtodolist.DTO.UserJoinDto;
import cjtodolist.springtodolist.DTO.UserDto;
import cjtodolist.springtodolist.entity.user.User;
import cjtodolist.springtodolist.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


// response 객체로 바꾸기 / 리턴값 확인 / 컨벤션 (코드 정렬)
@RequiredArgsConstructor
@Service
public class UserService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    public UserJoinDto add(UserJoinDto userJoinDto) {
        User user = User.builder()
                .username(userJoinDto.getUsername())
                .password(passwordEncoder.encode(userJoinDto.getPassword()))
                .nickname(userJoinDto.getNickname())
                .build();

        return new UserJoinDto(userRepository.save(user));
    }

    public User validateUser(UserDto userDto) {
        User findUser = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 ID 입니다.");
                });
        if (!passwordEncoder.matches(userDto.getPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return findUser;
    }

    public boolean isDuplicateUsername(UserJoinDto userJoinDto) {
        if (userRepository.findByUsername(userJoinDto.getUsername()).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void update(UserDto userDto) {
        User updateUser = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 ID 입니다.");
                });
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        updateUser.changePw(encodedPassword);
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
