package com.kata.cleanarchiauth.application;

import com.kata.cleanarchiauth.authentication.domain.DomainException;
import com.kata.cleanarchiauth.authentication.domain.User;
import com.kata.cleanarchiauth.authentication.usecases.AuthenticateUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticateUser authenticateUser;

    @Test
    void should_return_OK_when_user_exist() throws Exception {
        given(authenticateUser.handle(User.create("dexter", "killer")))
                .willReturn("hTYsHpOWFr4uU6NVmkzNbhrBWuBT1ZWF");
        mockMvc.perform(post("/api/auth").content(
                        """
                                {
                                  "name":"dexter",
                                  "password":"killer"
                                }
                                """
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("hTYsHpOWFr4uU6NVmkzNbhrBWuBT1ZWF"));
    }
    @Test
    void should_return_unauthorized_when_unknown_user() throws Exception {

        given(authenticateUser.handle(User.create("dertex", "killer")))
                .willThrow(DomainException.nameNotFound("dertex"));
        mockMvc.perform(post("/api/auth").content(
                        """
                                {
                                  "name":"dertex",
                                  "password":"killer"
                                }
                                """
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("""
                        {
                        "message": "le nom: dertex n'existe pas dans notre base de données"
                        }
                        """
                ));
    }

    @Test
    void should_return_unauthorized_when_incorrect_password() throws Exception {

        given(authenticateUser.handle(User.create("dexter", "killou")))
                .willThrow(DomainException.incorrectPassword("dexter"));
        mockMvc.perform(post("/api/auth").content(
                        """
                                {
                                  "name":"dexter",
                                  "password":"killou"
                                }
                                """
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("""
                        {
                        "message": "le mot de passe de l'utilisateur: dexter est incorrect"
                        }
                        """
                ));
    }

     @Test
     void should_return_bad_request_when_invalid_json() throws Exception {

         mockMvc.perform(post("/api/auth").content(
                         """
                                 {
                                   "name":"dexter"
                                 }
                                 """
                 ).contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isBadRequest())
                 .andExpect(content().json("""
                         {
                         "message": "le json est invalide pour les raisons suivantes : required properties are missing: password"
                         }
                         """
                 ));
     }

}
