package com.edu.unq.tpi.dapp.grupoB.Eventeando.serializer;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    public UserDeserializer(Class<User> t) {
        super(t);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        JsonNode name = node.get("name");
        JsonNode lastname = node.get("lastname");
        JsonNode email = node.get("email");

        JsonNode password = node.get("password");
        JsonNode birthday = node.get("birthday");

        return User.create(name.asText(), lastname.asText(), email.asText(), password.asText(), LocalDate.parse(birthday.textValue()));
    }
}