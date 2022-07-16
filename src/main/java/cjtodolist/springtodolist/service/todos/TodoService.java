package cjtodolist.springtodolist.service.todos;

import cjtodolist.springtodolist.entity.todo.Deadline;
import cjtodolist.springtodolist.entity.todo.State;
import cjtodolist.springtodolist.entity.todo.Todo;
import cjtodolist.springtodolist.entity.todo.TodoRepository;
import cjtodolist.springtodolist.DTO.TodoDto;
import cjtodolist.springtodolist.entity.user.User;
import cjtodolist.springtodolist.entity.user.UserRepository;
import cjtodolist.springtodolist.handleError.error.ErrorCode;
import cjtodolist.springtodolist.handleError.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public void add(TodoDto todoDto) {
        User findUser = userRepository.findByUsername(todoDto.getUsername())
                .orElseThrow(() -> {
                    throw new NotExistsException("존재하지 않는 User", ErrorCode.NOT_EXIST_ERROR);
                });
        Todo todo = Todo.builder()
                .content(todoDto.getContent())
                .user(findUser)
                .deadline(todoDto.getDeadline())
                .state(State.READY)
                .build();
        todoRepository.save(todo);
    }

    public TodoDto searchTodo(Long id) {
        Todo findTodo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotExistsException("존재하지 않는 Todo", ErrorCode.NOT_EXIST_ERROR);
                });
        return new TodoDto(findTodo);
    }

    public List<TodoDto> searchAll() {
        List<Todo> all = todoRepository.findAll();
        if (all.isEmpty()) {
            throw new NotExistsException("존재하지 않는 Todo", ErrorCode.NOT_EXIST_ERROR);
        }
        return all.stream()
                .map(result -> new TodoDto(result))
                .collect(Collectors.toList());
    }

    public List<TodoDto> searchDaily() {
        List<Todo> findTodos = todoRepository.findByDeadline(Deadline.DAILY);
        if (findTodos.isEmpty()) {
            throw new NotExistsException("존재하지 않는 Todo", ErrorCode.NOT_EXIST_ERROR);
        }
        return findTodos.stream()
                .map(result -> new TodoDto(result))
                .collect(Collectors.toList());
    }

    public List<TodoDto> searchWeekly() {
        List<Todo> findTodos = todoRepository.findByDeadline(Deadline.WEEKLY);
        if (findTodos.isEmpty()) {
            throw new NotExistsException("존재하지 않는 Todo", ErrorCode.NOT_EXIST_ERROR);
        }
        return findTodos.stream()
                .map(result -> new TodoDto(result))
                .collect(Collectors.toList());
    }

    public List<TodoDto> searchMonthly() {
        List<Todo> findTodos = todoRepository.findByDeadline(Deadline.MONTHLY);
        if (findTodos.isEmpty()) {
            throw new NotExistsException("존재하지 않는 Todo", ErrorCode.NOT_EXIST_ERROR);
        }
        return findTodos.stream()
                .map(result -> new TodoDto(result))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateTodo(Long id, TodoDto todoDto) {
        Todo findTodo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotExistsException("존재하지 않는 Todo", ErrorCode.NOT_EXIST_ERROR);
                });
        findTodo.updateContent(todoDto.getContent());
        findTodo.updateDeadline(todoDto.getDeadline());
    }

    @Transactional
    public void readyById(Long id) {
        Todo findTodo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotExistsException("존재하지 않는 Todo", ErrorCode.NOT_EXIST_ERROR);
                });
        findTodo.updateState(State.READY);
    }

    @Transactional
    public void ongoingById(Long id) {
        Todo findTodo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotExistsException("존재하지 않는 Todo", ErrorCode.NOT_EXIST_ERROR);
                });
        findTodo.updateState(State.ONGOING);
    }

    @Transactional
    public void completeById(Long id) {
        Todo findTodo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotExistsException("존재하지 않는 Todo", ErrorCode.NOT_EXIST_ERROR);
                });
        findTodo.updateState(State.COMPLETE);
    }


    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }
}
