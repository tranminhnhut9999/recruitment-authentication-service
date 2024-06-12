package project.springboot.template.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.springboot.template.dto.request.JwtRequest;
import project.springboot.template.dto.response.JwtResponse;
import project.springboot.template.entity.common.ApiResponse;
import project.springboot.template.service.JwtService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final JwtService jwtService;

    public AuthenticationController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody @Valid JwtRequest request) {
        return ResponseEntity.ok(ApiResponse.success(jwtService.login(request)));
    }
    // API: Forget password
    // API: Change Password
}
