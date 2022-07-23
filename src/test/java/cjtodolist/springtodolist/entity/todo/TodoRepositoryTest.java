//package cjtodolist.springtodolist.entity.todo;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.*;
//
//@DataJpaTest
//@Transactional
//class TodoRepositoryTest {
//
//    @Autowired
//    TodoRepository todoRepository;
//
//    // CREATE / READ 테스트
//    @Test
//    public void 투두저장및조회() throws Exception {
//        //given
//        Todo todo = Todo.builder()
//                .content("test1")
//                .state(State.READY)
//                .build();
//
//
//        //when
//        Long saveId = todoRepository.save(todo).getId();
//        Todo findTodo = todoRepository.findById(saveId).orElseThrow(()->new RuntimeException("존재하지 않는 todo"));
//
//        //then
//        assertThat(findTodo.getContent()).isEqualTo(todo.getContent());
//        assertThat(findTodo.getState()).isEqualTo(todo.getState());
//    }
//
//    // DELETE 테스트
//    @Test
//    public void 투두삭제() throws Exception {
//        //given
//        Todo todo = Todo.builder()
//                .content("deleteTest")
//                .state(State.READY)
//                .build();
//
//        Long saveId = todoRepository.save(todo).getId();
//
//        //when
//        todoRepository.deleteById(saveId);
//
//        //then
//        if(todoRepository.findById(saveId).isEmpty()) {
//            System.out.println("================== 삭제 성공 ==================");
//        }
//
//    }
//
//    // DELETE 테스트 2
//    @Test
//    public void 투두전체삭제() throws Exception {
//        //given
//        Todo todo2 = Todo.builder().content("todo2").state(State.READY).build();
//        Todo todo1 = Todo.builder().content("todo1").state(State.READY).build();
//
//        todoRepository.save(todo1);
//        todoRepository.save(todo2);
//
//        //when
//        todoRepository.deleteAll();
//
//        //then
//        if(todoRepository.findAll().isEmpty()){
//            System.out.println("=================== 전체 삭제 성공 ===================");
//        }
//    }
//
//
//
//    // UPDATE 테스트
//    @Test
//    public void 투두업데이트() throws Exception {
//        //given
//        Todo todo = Todo.builder()
//                .content("Test")
//                .state(State.READY)
//                .build();
//
//        // 영속성 객체 자동 업데이트 사용 -> save 시 반환되는 객체로 update
//        Todo saveTodo = todoRepository.save(todo);
//
//        //when
//        String newContent = "updateTest";
//        State newState = State.COMPLETE;
//        saveTodo.updateContent(newContent);
//        saveTodo.updateState(newState);
//
//        //then
//        assertThat(saveTodo.getContent()).isEqualTo(newContent);
//        assertThat(saveTodo.getState()).isEqualTo(State.COMPLETE);
//
//    }
//
//    // UPDATE 테스트 2
//    @Test
//    public void 투두업데이트2() throws Exception {
//        //given
//        Todo todo = Todo.builder()
//                .content("update2")
//                .state(State.READY)
//                .build();
//
//        Long saveId = todoRepository.save(todo).getId();
//        Todo updateTodo = todoRepository.findById(saveId).orElseThrow(()->new RuntimeException("잘못된 ID"));
//
//        //when
//        updateTodo.updateContent("newUpdate");
//        updateTodo.updateState(State.COMPLETE);
//        Todo update = todoRepository.save(updateTodo);
//
//        //then
//        assertThat(update.getContent()).isEqualTo(updateTodo.getContent());
//        assertThat(update.getState()).isEqualTo(updateTodo.getState());
//
//    }
//}