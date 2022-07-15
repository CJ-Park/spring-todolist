package cjtodolist.springtodolist.entity.todo;

import cjtodolist.springtodolist.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Deadline deadline;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    // 투두 수정
    public void updateContent(String newContent) {
        this.content = newContent;
    }
    public void updateDeadline(Deadline newDeadline) { this.deadline = newDeadline; }
    public void updateState(State newState) {
        this.state = newState;
    }
}