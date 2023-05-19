package com.kata.cleanarchiauth.authentication.domain;

public record User(String name, String password) {

    public static User create(String name, String password) {
        return new User(name, password);
    }
}
