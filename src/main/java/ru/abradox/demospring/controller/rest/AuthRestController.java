package ru.abradox.demospring.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abradox.demospring.model.dto.LoginRequest;
import ru.abradox.demospring.model.entity.User;
import ru.abradox.demospring.model.repository.UserRepository;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthRestController {

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        var userOpt = userRepository.findByLogin(loginRequest.getLogin());
        if (userOpt.isEmpty()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        var user = userOpt.get();
        if (!user.getPassword().equals(loginRequest.getPassword())) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(user.getLogin());
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody LoginRequest loginRequest) {
        if (userRepository.existsByLogin(loginRequest.getLogin())) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        var user = userRepository.save(new User(loginRequest.getLogin(), loginRequest.getPassword()));
        return ResponseEntity.ok(user.getLogin());
    }
}
