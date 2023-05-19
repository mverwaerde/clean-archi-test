package com.kata.cleanarchiauth.application;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UserValidatorTest {

    @Test
    void should_do_nothing_when_json_is_valid() {
        String validJson = """
                {"name":"John Doe",
                 "password": "password"}
                """;

        UserValidator userValidator = new UserValidator();

        assertDoesNotThrow(() -> userValidator.validate(validJson));
    }

    @Test
    void should_give_user_information_about_missing_field() {
        String invalidJson = """
                {"name":"John Doe"}
                """;

        UserValidator userValidator = new UserValidator();

        assertThatThrownBy(() -> userValidator.validate(invalidJson))
                .isInstanceOf(InvalidJsonException.class)
                .hasMessage("le json est invalide pour les raisons suivantes : required properties are missing: password");
    }

    @Test
    void should_give_user_information_about_validation_errors() {
        String invalidJson = """
                {"name":1}
                """;

        UserValidator userValidator = new UserValidator();

        assertThatThrownBy(() -> userValidator.validate(invalidJson))
                .isInstanceOf(InvalidJsonException.class)
                .hasMessage("le json est invalide pour les raisons suivantes : required properties are missing: password,\nexpected type: string, actual: integer");
    }

}
