package com.company.authservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.company.authservice.dto.SignupDto;
import com.company.authservice.exception.DatabaseException;

@Repository(value = "AuthRepository")
public class AuthRepository {

    private final JdbcTemplate _JdbcTemplate;

    @Autowired
    public AuthRepository(JdbcTemplate jdbcTemplate) {
        this._JdbcTemplate = jdbcTemplate;
    }

    public boolean SignUp(SignupDto cred, StringBuffer error) {
        try {
            String query = "insert into users (u_name, u_email, u_password) values (?, ?, ?)";
            _JdbcTemplate.update(query, cred.get_Name(), cred.get_Email(), cred.get_Password());
        } catch (Exception ex) {
            error.append(ex.getMessage());
            System.out.println("Error during user signup: " + error);
            throw new DatabaseException("Database error during signup: " + ex.getMessage());
        }
        return true;
    }

    public Boolean getPasswordFromEmail(String email, StringBuffer password, StringBuffer error) {
        try {
            String query = "select u_password from users where u_email = ?";
            password.append(_JdbcTemplate.queryForObject(query, String.class, email));
        } catch (Exception ex) {
            error.append(ex.getMessage());
            System.out.println("Error during user authentication: " + error);
            throw new DatabaseException("Database error during authentication: " + ex.getMessage());
        }
        return true;
    }
}
