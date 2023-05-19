package com.kata.cleanarchiauth.application;

import com.kata.cleanarchiauth.authentication.domain.TokenDispenser;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenDispenser implements TokenDispenser {

    @Override
    public String give() {
        return new AuthToken().generate();
    }

    record AuthToken() {

        String generate() {
            return "6ROrLF0T7w2yqrNEdla151Mv39FwB27y";
        }
    }

}
