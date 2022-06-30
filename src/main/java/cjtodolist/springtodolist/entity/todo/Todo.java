package cjtodolist.springtodolist.entity.todo;

import cjtodolist.springtodolist.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean completed;

    // 투두 수정
    public void updateContent(String newContent) {
        this.content = newContent;
    }

    public void updateComplete(Boolean newComplete){
        this.completed = newComplete;
    }

    public void isCompleted(){
        this.completed = true;
    }
}