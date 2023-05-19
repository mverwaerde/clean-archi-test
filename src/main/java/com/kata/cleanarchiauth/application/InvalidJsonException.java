package com.kata.cleanarchiauth.application;

import com.github.erosb.jsonsKema.ValidationFailure;

import java.util.Set;
import java.util.stream.Collectors;

public class InvalidJsonException extends RuntimeException {
    public InvalidJsonException(String message) {
        super(message);
    }

    public static InvalidJsonException invalidJsonUser(Set<ValidationFailure> failures) {
        return new InvalidJsonException("le json est invalide pour les raisons suivantes : " + failures.stream().map(ValidationFailure::getMessage).collect(Collectors.joining(",\n")));
    }
}
