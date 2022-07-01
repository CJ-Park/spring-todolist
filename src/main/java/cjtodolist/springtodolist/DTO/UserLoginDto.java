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
public class UserLoginDto {
    private String username;
    private String password;

    public UserLoginDto(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
