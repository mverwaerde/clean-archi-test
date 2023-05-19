package com.kata.cleanarchiauth.authentication.domain;


import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    boolean userNameExists(User user);

    boolean passwordIsCorrect(User user);
}
