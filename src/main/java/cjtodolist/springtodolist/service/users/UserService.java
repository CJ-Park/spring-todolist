package cjtodolist.springtodolist.service.users;

import cjtodolist.springtodolist.DTO.UserJoinDto;
import cjtodolist.springtodolist.DTO.UserLoginDto;
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

    public User validateUser(UserLoginDto userLoginDto) {
        User findUser = userRepository.findByUsername(userLoginDto.getUsername())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 ID 입니다."));
        if(!userLoginDto.getPassword().equals(findUser.getPassword())) {
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
}
