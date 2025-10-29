package com.company.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.authservice.dto.AuthDto;
import com.company.authservice.dto.ReturnResponseDto;
import com.company.authservice.dto.SignupDto;
import com.company.authservice.exception.DatabaseException;
import com.company.authservice.exception.UserAlreadyExistsException;
import com.company.authservice.exception.ValidationException;
import com.company.authservice.repository.AuthRepository;

@Service
public class AuthService {

    private final AuthRepository _AuthRepository;
    private final PasswordEncoder _PasswordEncoder;

    @Autowired
    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this._AuthRepository = authRepository;
        this._PasswordEncoder = passwordEncoder;
    }

    public boolean SignUp(SignupDto cred, ReturnResponseDto response) {

        if (cred == null || cred.get_Email() == null || cred.get_Email().isBlank()) {
            throw new ValidationException("Email cannot be empty");
        }

        cred.set_Password(_PasswordEncoder.encode(cred.get_Password()));
        StringBuffer status = new StringBuffer();
        boolean isSuccess;

        try {
            isSuccess = _AuthRepository.SignUp(cred, status);
            if (!isSuccess) {
                throw new UserAlreadyExistsException("User registration failed: " + status.toString());
            }
            response.setStatus("User registration successful.");
        } catch (UserAlreadyExistsException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseException("User registration failed due to database error: " + ex.getMessage());
        }

        response.setEmail(cred.get_Email());
        return isSuccess;
    }

    public boolean Authenticate(AuthDto cred) {
        if (cred == null || cred.get_Email() == null || cred.get_Email().isBlank()) {
            throw new ValidationException("Email cannot be empty");
        }
        String email = cred.get_Email();
        String password = cred.get_Password();
        StringBuffer status = new StringBuffer();
        StringBuffer passwordFromDB = new StringBuffer();
        Boolean isSuccess;

        try {
            isSuccess = _AuthRepository.getPasswordFromEmail(email, passwordFromDB, status);
            if (!isSuccess) {
                throw new UserAlreadyExistsException("Authentication failed: " + status.toString());
            }
            return isSuccess && _PasswordEncoder.matches(password, passwordFromDB.toString());
        } catch (UserAlreadyExistsException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseException("Authentication failed due to database error: " + ex.getMessage());
        }
    }
}
