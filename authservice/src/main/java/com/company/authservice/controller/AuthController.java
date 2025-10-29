package com.company.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.authservice.dto.AuthDto;
import com.company.authservice.dto.ReturnResponseDto;
import com.company.authservice.dto.SignupDto;
import com.company.authservice.exception.UserAlreadyExistsException;
import com.company.authservice.exception.ValidationException;
import com.company.authservice.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService _AuthService;

    @Autowired
    public AuthController(AuthService authService) {
        this._AuthService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ReturnResponseDto> SignUp(@RequestBody SignupDto cred) {
        ReturnResponseDto response = new ReturnResponseDto();
        try {
            boolean isSuccess = _AuthService.SignUp(cred, response);
            if (isSuccess) {
                return ResponseEntity.ok(response);
            } else {
                response.setStatus("User registration failed.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (UserAlreadyExistsException ex) {
            response.setStatus(ex.getMessage());
            response.setEmail(cred.get_Email());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (ValidationException ex) {
            response.setStatus(ex.getMessage());
            response.setEmail(cred.get_Email());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            response.setStatus("User registration failed: " + ex.getMessage());
            response.setEmail(cred.get_Email());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> Authenticate(@RequestBody AuthDto cred) {
        try {
            boolean isAuthenticated = _AuthService.Authenticate(cred);
            if (isAuthenticated) {
                return ResponseEntity.ok("Authentication successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
            }
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication error: " + ex.getMessage());
        }
    }
}
