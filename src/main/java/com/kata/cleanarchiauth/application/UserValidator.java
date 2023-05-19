package com.kata.cleanarchiauth.application;

import com.github.erosb.jsonsKema.*;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private final Validator validator;

    public UserValidator() {
        JsonValue schemaJson = new JsonParser(USER_SCHEMA).parse();
        Schema schema = new SchemaLoader(schemaJson).load();
        validator = Validator.forSchema(schema);
    }

    private static final String USER_SCHEMA = """
            {
              "$schema": "https://json-schema.org/draft/2020-12/schema",
              "$id": "user.schema.json",
              "title": "User",
              "description": "A user from AuthAPI",
              "type": "object",
              "properties": {
                "name": {
                  "description": "Name of the user",
                  "type": "string"
                },
                "password": {
                  "description": "password of the user",
                  "type": "string"
                }
              },
              "required": [ "name", "password" ]
            }
                        
            """;


    public void validate(String input) {
        JsonValue instance = new JsonParser(input).parse();
        ValidationFailure failure = validator.validate(instance);

        if (failure != null) {
            throw InvalidJsonException.invalidJsonUser(failure.getCauses());
        }
    }
}
