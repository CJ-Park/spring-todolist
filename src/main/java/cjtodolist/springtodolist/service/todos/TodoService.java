package cjtodolist.springtodolist.service.todos;

import cjtodolist.springtodolist.DTO.TodoDeadlineDto;
import cjtodolist.springtodolist.DTO.TodoStateDto;
import cjtodolist.springtodolist.entity.todo.State;
import cjtodolist.springtodolist.entity.todo.Todo;
import cjtodolist.springtodolist.entity.todo.TodoRepository;
import cjtodolist.springtodolist.DTO.TodoDto;
import cjtodolist.springtodolist.entity.user.User;
import cjtodolist.springtodolist.entity.user.UserRepository;
import cjtodolist.springtodolist.handleError.error.ErrorCode;
import cjtodolist.springtodolist.handleError.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
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

    @Transactional
    public TodoDto searchTodo(Long id) {
        Todo findTodo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotExistsException("존재하지 않는 Todo", ErrorCode.NOT_EXIST_ERROR);
                });
        return new TodoDto(findTodo);
    }

    @Transactional
    public Page<TodoDto> getTodoList(Pageable pageable) {
        Page<Todo> todoList = todoRepository.findAll(pageable);

        Page<TodoDto> pagingTodoList = todoList.map(
                todo -> new TodoDto(
                        todo.getId(), todo.getContent(),
                        todo.getUser().getUsername(), todo.getDeadline(),
                        todo.getState()
                )
        );
        return pagingTodoList;
    }

    @Transactional
    public Page<TodoDto> searchByDeadline(Pageable pageable, TodoDeadlineDto dto) {
        Page<Todo> findTodos = todoRepository.findByDeadline(pageable, dto.getDeadline());
        Page<TodoDto> findTodoDtos = findTodos.map(
                todo -> new TodoDto(
                        todo.getId(), todo.getContent(),
                        todo.getUser().getUsername(), todo.getDeadline(),
                        todo.getState()
                )
        );
        return findTodoDtos;
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
    public void updateState(Long id, TodoStateDto dto) {
        Todo findTodo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotExistsException("존재하지 않는 Todo", ErrorCode.NOT_EXIST_ERROR);
                });
        findTodo.updateState(dto.getState());
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }
}
