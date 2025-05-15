package com.nisum.nisum.infrastructure.db.schema;

import com.nisum.nisum.entity.user.model.Phone;
import com.nisum.nisum.entity.user.model.User;
import com.nisum.nisum.infrastructure.user.dto.UserRegistrationData;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserSchemaTest {

    @Test
    void shouldInitializeFieldsFromUserRegistrationData() {
        UserRegistrationData userData = new UserRegistrationData("Juan", "juan@test.com", "Password1!", List.of(
                new Phone("1234567", "1", "57")
        ));

        UserSchema schema = new UserSchema(userData, "token123");

        assertEquals("juan@test.com", schema.getEmail());
        assertEquals("Juan", schema.getName());
        assertEquals("Password1!", schema.getPassword());
        assertEquals("token123", schema.getToken());
        assertNotNull(schema.getCreated());
        assertNotNull(schema.getModified());
        assertNotNull(schema.getLastLogin());
        assertEquals(1, schema.getPhones().size());
        assertEquals(schema, schema.getPhones().get(0).getUser());
    }

    @Test
    void shouldConvertToUserDomainModel() {
        UserRegistrationData userData = new UserRegistrationData("Ana", "ana@example.com", "Secret123$",
                Collections.singletonList(new Phone("7654321", "2", "34")));

        UserSchema schema = new UserSchema(userData, "secure-token");
        ReflectionTestUtils.setField(schema, "id", 1L);

        User user = schema.toUser();

        assertEquals("ana@example.com", user.getEmail());
        assertEquals("Ana", user.getName());
        assertEquals("Secret123$", user.getPassword());
        assertEquals("secure-token", user.getToken());
        assertTrue(user.isActive());
        assertEquals(1, user.getPhones().size());
        assertEquals("7654321", user.getPhones().get(0).getNumber());
    }

    @Test
    void shouldSupportDefaultConstructor() {
        UserSchema schema = new UserSchema();
        assertNotNull(schema);
    }
}
