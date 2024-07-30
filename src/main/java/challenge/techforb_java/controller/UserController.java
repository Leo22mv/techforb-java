package challenge.techforb_java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import challenge.techforb_java.controller.request.LoginForm;
import challenge.techforb_java.entity.User;
import challenge.techforb_java.service.impl.UserServiceImpl;
import challenge.techforb_java.service.JwtService;

@RestController
@RequestMapping("/user")
// @CrossOrigin(origins = "https://techforb-challenge.web.app")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.createUser(user);
            return ResponseEntity.ok(user);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody User userDetails) {  
        try {
            userService.editUser(id, userDetails);
            return ResponseEntity.ok().body("Usuario editado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginForm loginForm) {
        List<User> usersList = userService.getUsers();
        for (User user : usersList) {
            if (loginForm.getEmail().equals(user.getEmail()) && passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(jwtService.generateToken(user));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
}
