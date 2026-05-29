package co.edu.unbosque.horaclass.auth.controller;

import co.edu.unbosque.horaclass.auth.dto.ForgotPasswordRequestDto;
import co.edu.unbosque.horaclass.auth.dto.LoginRequestDto;
import co.edu.unbosque.horaclass.auth.dto.LoginResponseDto;
import co.edu.unbosque.horaclass.auth.dto.MessageResponseDto;
import co.edu.unbosque.horaclass.auth.dto.ResetPasswordRequestDto;
import co.edu.unbosque.horaclass.auth.service.PasswordResetService;
import co.edu.unbosque.horaclass.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        } catch (AuthenticationException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUserName(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponseDto response = new LoginResponseDto(jwtToken, userDetails.getUsername(), roles);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponseDto> forgotPassword(@RequestBody ForgotPasswordRequestDto request) {
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            return ResponseEntity.badRequest().body(new MessageResponseDto("Debes ingresar tu usuario o correo."));
        }
        return ResponseEntity.ok(passwordResetService.requestPasswordReset(request.getUsername()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestDto request) {
        if (request.getToken() == null || request.getToken().isBlank()) {
            return ResponseEntity.badRequest().body(new MessageResponseDto("Token de recuperación inválido."));
        }
        try {
            return ResponseEntity.ok(passwordResetService.resetPassword(request.getToken(), request.getNewPassword()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new MessageResponseDto(ex.getMessage()));
        }
    }
}
