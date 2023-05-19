package com.kata.cleanarchiauth.authentication.usecases;

import com.kata.cleanarchiauth.application.AuthTokenDispenser;
import com.kata.cleanarchiauth.authentication.domain.DomainException;
import com.kata.cleanarchiauth.authentication.domain.TokenDispenser;
import com.kata.cleanarchiauth.authentication.domain.User;
import com.kata.cleanarchiauth.authentication.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserTest {
    @Mock
    private UserRepository userRepository;
    private final TokenDispenser tokenDispenser = new AuthTokenDispenser();

    @Test
    void authentication_success_should_lean_on_give_a_token() {
        AuthenticateUser authenticateUser = new AuthenticateUser(userRepository, tokenDispenser);
        User user = User.create("dexter", "killer");
        given(userRepository.userNameExists(user)).willReturn(true);
        given(userRepository.passwordIsCorrect(user)).willReturn(true);

        String authToken = authenticateUser.handle(user);

        Assertions.assertNotNull(authToken);
    }

    @Test
    void authentication_should_give_user_a_message_when_user_does_not_exist() {
        AuthenticateUser authenticateUser = new AuthenticateUser(userRepository, tokenDispenser);
        User user = User.create("john", "killer");
        given(userRepository.userNameExists(user)).willReturn(false);

        assertThatThrownBy(() -> authenticateUser.handle(user))
                .isInstanceOf(DomainException.class)
                .hasMessage("le nom: john n'existe pas dans notre base de données");
    }

    @Test
    void authentication_should_give_user_a_message_when_password_is_incorrect() {
        AuthenticateUser authenticateUser = new AuthenticateUser(userRepository, tokenDispenser);
        User user = User.create("dexter", "wrongPassword");
        given(userRepository.userNameExists(user)).willReturn(true);
        given(userRepository.passwordIsCorrect(user)).willReturn(false);

        assertThatThrownBy(() -> authenticateUser.handle(user))
                .isInstanceOf(DomainException.class)
                .hasMessage("le mot de passe de l'utilisateur: dexter est incorrect");
    }


}
