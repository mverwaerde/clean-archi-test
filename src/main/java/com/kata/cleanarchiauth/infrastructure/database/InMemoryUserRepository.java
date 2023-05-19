package com.kata.cleanarchiauth.infrastructure.database;

import com.kata.cleanarchiauth.authentication.domain.User;
import com.kata.cleanarchiauth.authentication.domain.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public final class InMemoryUserRepository implements UserRepository {

    @Override
    public boolean userNameExists(User user) {
        return "dexter".equals(user.name());
    }

    @Override
    public boolean passwordIsCorrect(User user) {
        return "killer".equals(user.name());
    }
}
