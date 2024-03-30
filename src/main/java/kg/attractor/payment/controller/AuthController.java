package kg.attractor.payment.controller;

import kg.attractor.payment.dto.UserDto;
import kg.attractor.payment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> createUser(UserDto user) {
        userService.createUser(user);
        return ResponseEntity.ok("Registration successfull");
    }
}
