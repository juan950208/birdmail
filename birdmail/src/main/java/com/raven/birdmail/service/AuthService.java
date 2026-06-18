package com.raven.birdmail.service;

import com.raven.birdmail.repository.UserRepository;
import com.raven.birdmail.constant.GlobalConstants;
import com.raven.birdmail.dto.LoginDTO;
import com.raven.birdmail.exception.InvalidCredentialsException;
import com.raven.birdmail.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public User login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail() + GlobalConstants.DOMAIN);

        if (user == null) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
        boolean passwordIsValid = argon2.verify(
                user.getPasswordHash(),
                loginDTO.getPassword());

        if (!passwordIsValid) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return user;
    }
}
