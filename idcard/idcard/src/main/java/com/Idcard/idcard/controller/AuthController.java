package com.Idcard.idcard.controller;

// package com.Idcard.idcard;
import jakarta.servlet.http.HttpSession;
// import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Idcard.idcard.entity.Student;
import com.Idcard.idcard.entity.User;
import com.Idcard.idcard.repository.StudentRepository;
import com.Idcard.idcard.repository.UserRepository;
import com.Idcard.idcard.service.AuthService;
import java.util.Collections;
import java.util.HashMap;
// import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
// @CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String token = authService.authenticate(request.get("username"), request.get("password"));
        User user = userRepository.findByToken(token);
        Map<String, String> response = new HashMap<>();
        if (token != null) {
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("role", user.getRole());
            response.put("username", user.getUsername());
        } else {
            response.put("message", "Invalid credentials");
        }
        return response;
    }

    // @PostMapping("/logout")
    // public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
    // String token = request.get("token");
    // System.out.println("token: " + token);
    // if (token == null || token.isEmpty()) {
    // return ResponseEntity.badRequest().body("Error: Token is required for logout");
    // }

    // authService.logout1(token);
    // return ResponseEntity.ok(Collections.singletonMap("message", "Logged out successfully"));
    // }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String token) {
        System.out.println("Received Token for Logout: " + token);

        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Token is required for logout");
        }

        authService.logout1(token);
        return ResponseEntity.ok(Collections.singletonMap("message", "Logged out successfully"));
    }


    @GetMapping("/check-session")
    public String checkSession(HttpSession session) {
        String username = (String) session.getAttribute("username");
        return (username != null) ? "User: " + username + " is logged in" : "No active session";
    }

    @GetMapping("/getTokenByUser")
    public User getUserByUsertoken(@RequestParam("TOKEN") String token) {
        return userRepository.findByToken(token);
    }

    @GetMapping("/currentStudent")
    public ResponseEntity<Student> getLoggedInStudent(@RequestParam("username") String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            System.out.println("role: " + userOpt.get().getRole());
            if ("STUDENT".equals(user.getRole())) {
                Optional<Student> studentOpt = studentRepository.findByRollNo(user.getUsername());
                // System.out.println("student: " + studentOpt);
                return studentOpt.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
