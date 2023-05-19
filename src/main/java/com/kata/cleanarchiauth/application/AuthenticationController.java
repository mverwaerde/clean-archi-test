package com.kata.cleanarchiauth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.cleanarchiauth.authentication.domain.DomainException;
import com.kata.cleanarchiauth.authentication.domain.User;
import com.kata.cleanarchiauth.authentication.usecases.AuthenticateUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private final AuthenticateUser authenticateUser;
    private final UserValidator userValidator = new UserValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    public AuthenticationController(AuthenticateUser authenticateUser) {
        this.authenticateUser = authenticateUser;
    }

    @PostMapping(value = "/auth")
    public String authenticate(@RequestBody String user) throws JsonProcessingException {
        userValidator.validate(user);
        return authenticateUser.handle(mapper.readValue(user, User.class));
    }

    private record ErrorMessage(String message) {
    }

    @ExceptionHandler(value = DomainException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage catchDomainException(DomainException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(value = InvalidJsonException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage catchJsonException(InvalidJsonException exception) {
        return new ErrorMessage(exception.getMessage());
    }
}
