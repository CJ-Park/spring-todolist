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
public class UserDto {
    private String username;
    private String password;

    public UserDto(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
