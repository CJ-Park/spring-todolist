package cjtodolist.springtodolist.DTO;

import cjtodolist.springtodolist.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinDto {
    private Long id;
    private String username;
    private String password;
    private String nickname;

    public UserJoinDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
    }
}
