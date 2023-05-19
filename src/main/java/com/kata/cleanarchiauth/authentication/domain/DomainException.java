package com.kata.cleanarchiauth.authentication.domain;

public class DomainException extends RuntimeException {

    private DomainException(String message) {
        super(message);
    }

    public static DomainException nameNotFound(String name) {
        return new DomainException("le nom: " + name + " n'existe pas dans notre base de données");
    }

    public static DomainException incorrectPassword(String name) {
        return new DomainException("le mot de passe de l'utilisateur: " + name + " est incorrect");
    }
}
