package org.latexscribe.LatexScribe.webcontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.latexscribe.LatexScribe.domain.dto.AuthRequestDto;
import org.latexscribe.LatexScribe.domain.dto.AuthResponseDto;
import org.latexscribe.LatexScribe.domain.dto.RegisterRequestDto;
import org.latexscribe.LatexScribe.domain.dto.UsersDto;
import org.latexscribe.LatexScribe.domain.model.User;
import org.latexscribe.LatexScribe.service.IAuthService;
import org.latexscribe.LatexScribe.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService service;
    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto request) {

        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(service.refreshToken(request, response));
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UsersDto>> allUsers() throws IOException {
        return ResponseEntity.ok(userService.listAll());
    }

//    @GetMapping("/currentUser")
//    public ResponseEntity<UsersDto> currentUser() throws IOException {
//
//        return ResponseEntity.ok(userService.getCurrentUser());
//    }


    @GetMapping("/currentRole")
    public ResponseEntity<String> getCurrentUser(@RequestBody String username) {
        String result = "unknown";
        User user = userService.findByUsername(username).orElse(null);
        if (user != null) result = user.getRole().toString();
        System.out.println("role is"+result);
        return ResponseEntity.ok(result);
    }


}
