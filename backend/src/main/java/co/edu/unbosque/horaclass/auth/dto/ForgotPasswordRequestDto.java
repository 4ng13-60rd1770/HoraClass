package co.edu.unbosque.horaclass.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForgotPasswordRequestDto {
    private String username;
}
