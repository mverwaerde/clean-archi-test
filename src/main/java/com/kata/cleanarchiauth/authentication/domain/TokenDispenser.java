package com.kata.cleanarchiauth.authentication.domain;

import org.springframework.stereotype.Service;

@Service
public interface TokenDispenser {
    String give();
}
