package cjtodolist.springtodolist.service.users;

import cjtodolist.springtodolist.DTO.UserJoinDto;
import cjtodolist.springtodolist.DTO.UserDto;
import cjtodolist.springtodolist.entity.user.User;
import cjtodolist.springtodolist.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    final UserRepository userRepository;

    public User add(UserJoinDto userJoinDto) {
        User user = User.builder()
                .username(userJoinDto.getUsername())
                .password(userJoinDto.getPassword())
                .nickname(userJoinDto.getNickname())
                .build();

        return userRepository.save(user);
    }

    public User validateUser(UserDto userDto) {
        User findUser = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(()-> {throw new IllegalArgumentException("존재하지 않는 ID 입니다.");});
        if(!userDto.getPassword().equals(findUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return findUser;
    }

    public boolean isDuplicateUsername(UserJoinDto userJoinDto) {
        if(userRepository.findByUsername(userJoinDto.getUsername()).isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

    public User update(UserDto userDto) {
        User updateUser = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(()-> {throw new IllegalArgumentException("존재하지 않는 ID 입니다.");});
        updateUser.changePw(userDto.getPassword());
        return userRepository.save(updateUser);
    }

    public void delete(UserDto userDto) {
        User deleteUser = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(()->{throw new IllegalArgumentException("존재하지 않는 ID 입니다.");});
        if(!userDto.getPassword().equals(deleteUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        userRepository.delete(deleteUser);
    }
}
