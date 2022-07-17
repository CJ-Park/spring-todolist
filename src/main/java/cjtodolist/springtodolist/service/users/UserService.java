package cjtodolist.springtodolist.service.users;

import cjtodolist.springtodolist.DTO.TodoDto;
import cjtodolist.springtodolist.DTO.UserJoinDto;
import cjtodolist.springtodolist.DTO.UserDto;
import cjtodolist.springtodolist.config.JwtTokenProvider;
import cjtodolist.springtodolist.entity.todo.Todo;
import cjtodolist.springtodolist.entity.user.User;
import cjtodolist.springtodolist.entity.user.UserRepository;
import cjtodolist.springtodolist.handleError.error.ErrorCode;
import cjtodolist.springtodolist.handleError.exception.IdDuplicateException;
import cjtodolist.springtodolist.handleError.exception.LoginFailedException;
import cjtodolist.springtodolist.handleError.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                    throw new NotExistsException("존재하지 않는 ID", ErrorCode.NOT_EXIST_ERROR);
                });
        if (!passwordEncoder.matches(userDto.getPassword(), findUser.getPassword())) {
            throw new LoginFailedException("잘못된 비밀번호", ErrorCode.LOGIN_FAILED_ERROR);
        }
        return jwtTokenProvider.createToken(findUser.getUsername(), findUser.getRoles());
    }

    public void isDuplicatedUser(UserJoinDto userJoinDto) {
        if (userRepository.findByUsername(userJoinDto.getUsername()).isPresent()) {
            throw new IdDuplicateException("이미 사용중인 ID", ErrorCode.ID_DUPLICATION);
        }
    }

    public void update(UserDto userDto) {
        User updateUser = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> {
                    throw new NotExistsException("존재하지 않는 ID", ErrorCode.NOT_EXIST_ERROR);
                });
        updateUser.changePw(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(updateUser);
    }

    public void delete(UserDto userDto) {
        User deleteUser = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> {
                    throw new NotExistsException("존재하지 않는 ID", ErrorCode.NOT_EXIST_ERROR);
                });
        if (!passwordEncoder.matches(userDto.getPassword(), deleteUser.getPassword())) {
            throw new LoginFailedException("잘못된 비밀번호", ErrorCode.LOGIN_FAILED_ERROR);
        }
        userRepository.delete(deleteUser);
    }

    public Page<TodoDto> getTodos(Pageable pageable, Long id) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotExistsException("존재하지 않는 ID", ErrorCode.NOT_EXIST_ERROR);
                });
        List<Todo> todos = findUser.getTodos();
        Page<Todo> pageTodo = new PageImpl<>(todos, pageable, todos.size());
        Page<TodoDto> pageDto = pageTodo.map(
                todo -> new TodoDto(
                        todo.getId(), todo.getContent(),
                        todo.getUser().getUsername(), todo.getDeadline(),
                        todo.getState()
                )
        );
        return pageDto;
    }
}
