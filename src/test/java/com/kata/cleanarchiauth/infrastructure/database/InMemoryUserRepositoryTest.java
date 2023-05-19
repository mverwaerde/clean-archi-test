package com.kata.cleanarchiauth.infrastructure.database;

import com.kata.cleanarchiauth.authentication.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;

class InMemoryUserRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new InMemoryUserRepository();
    }

}
