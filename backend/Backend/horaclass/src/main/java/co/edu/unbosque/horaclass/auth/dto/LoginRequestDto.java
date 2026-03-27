package co.edu.unbosque.horaclass.auth.dto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;
}
