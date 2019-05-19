package com.edu.unq.tpi.dapp.grupoB.Eventeando.serializer;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.UserException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class UserDeserializer extends StdDeserializer<User> {

    public UserDeserializer() {
        this(null);
    }

    public UserDeserializer(Class<User> klazz) {
        super(klazz);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode jsonNode = oc.readTree(jsonParser);

        String name = parseString(jsonNode, "name");
        String lastname = parseString(jsonNode, "lastname");
        String email = parseString(jsonNode, "email");
        String password = parseString(jsonNode, "password");
        LocalDate birthday = parseDate(jsonNode, "birthday");

        return createUser(name, lastname, email, password, birthday);
    }

    private User createUser(String name, String lastname, String email, String password, LocalDate birthday) {
        try {
            return User.create(name, lastname, email, password, birthday);
        } catch (UserException e) {
            throw new InvalidCreation(e.getMessage());
        }
    }

    private LocalDate parseDate(JsonNode node, String field) {
        return LocalDate.parse(parseString(node, field));
    }

    private String parseString(JsonNode node, String name) {
        return node.get(name).asText();
    }
}