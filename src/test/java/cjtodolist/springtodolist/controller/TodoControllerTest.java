package cjtodolist.springtodolist.controller;

import cjtodolist.springtodolist.DTO.TodoDto;
import cjtodolist.springtodolist.entity.todo.Todo;
import cjtodolist.springtodolist.entity.todo.TodoRepository;
import cjtodolist.springtodolist.service.todos.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// 단위 테스트
@WebMvcTest
class TodoControllerTest {

}