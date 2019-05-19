package com.edu.unq.tpi.dapp.grupoB.Eventeando.serializer;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {

    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User anUser, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", anUser.id());
        jgen.writeStringField("name", anUser.name());
        jgen.writeStringField("lastname", anUser.lastname());
        jgen.writeStringField("email", anUser.email());
        jgen.writeObjectField("birthday", anUser.birthday());

        jgen.writeEndObject();
    }
}

