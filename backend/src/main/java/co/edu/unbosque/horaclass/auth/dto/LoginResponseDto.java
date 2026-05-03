package co.edu.unbosque.horaclass.auth.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponseDto {
    private String jwtToken;
    private String username;
    private List<String> roles;

}
