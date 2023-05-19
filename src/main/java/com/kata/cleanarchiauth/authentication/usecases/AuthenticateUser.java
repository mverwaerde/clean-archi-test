package com.kata.cleanarchiauth.authentication.usecases;

import com.kata.cleanarchiauth.authentication.domain.DomainException;
import com.kata.cleanarchiauth.authentication.domain.TokenDispenser;
import com.kata.cleanarchiauth.authentication.domain.User;
import com.kata.cleanarchiauth.authentication.domain.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateUser {
    private final UserRepository userRepository;
    private final TokenDispenser tokenDispenser;

    public AuthenticateUser(UserRepository userRepository, TokenDispenser tokenDispenser) {
        this.userRepository = userRepository;
        this.tokenDispenser = tokenDispenser;
    }

    public String handle(User user) {
        if (!userRepository.userNameExists(user)) {
            throw DomainException.nameNotFound(user.name());
        }
        if (!userRepository.passwordIsCorrect(user)) {
            throw DomainException.incorrectPassword(user.name());
        }
        return tokenDispenser.give();
    }
}

