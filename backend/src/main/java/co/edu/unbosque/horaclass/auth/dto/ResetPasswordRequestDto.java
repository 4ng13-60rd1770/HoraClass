package co.edu.unbosque.horaclass.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordRequestDto {
    private String token;
    private String newPassword;
}
