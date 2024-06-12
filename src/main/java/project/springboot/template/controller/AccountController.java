package project.springboot.template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.springboot.template.dto.request.ChangePasswordRequest;
import project.springboot.template.dto.request.RegisterRequest;
import project.springboot.template.dto.response.ProfileResponse;
import project.springboot.template.entity.common.ApiResponse;
import project.springboot.template.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PutMapping("/password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @RequestBody ChangePasswordRequest request) {
        accountService.changePassword(request);
        return ResponseEntity.ok(ApiResponse.success("change password successful"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @RequestBody RegisterRequest request
    ) {
        boolean createUserResult = accountService.createNewUser(request);
        if (createUserResult) {
            return ResponseEntity.ok(ApiResponse.success("create user successful"));
        } else {
            return ResponseEntity.ok(ApiResponse.failed("create user failed"));
        }
    }
    // API: InActive and Active account

    // API: Get Account Profile
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfileByUser() {
        return ResponseEntity.ok(ApiResponse.success(this.accountService.getUserProfileByUser()));
    }


    // API: Update Profile

    // API: Change Role Account

    // API: Reset Password -> use when user forget password

    // Verify Token
    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<Boolean>> verifyUser() {
        return ResponseEntity.ok(ApiResponse.success(true));
    }
}
