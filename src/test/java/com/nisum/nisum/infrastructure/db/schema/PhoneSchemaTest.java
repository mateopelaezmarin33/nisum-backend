package com.nisum.nisum.infrastructure.db.schema;

import com.nisum.nisum.entity.user.model.Phone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneSchemaTest {

    @Test
    void shouldMapFieldsFromPhoneDomain() {
        Phone phone = new Phone("1234567", "1", "57");
        PhoneSchema phoneSchema = new PhoneSchema(phone);

        assertEquals("1234567", phoneSchema.getNumber());
        assertEquals("1", phoneSchema.getCityCode());
        assertEquals("57", phoneSchema.getCountryCode());
    }

    @Test
    void shouldSetAndGetUser() {
        UserSchema user = new UserSchema();
        PhoneSchema phoneSchema = new PhoneSchema();
        phoneSchema.setUser(user);

        assertEquals(user, phoneSchema.getUser());
    }

    @Test
    void shouldSupportDefaultConstructor() {
        PhoneSchema phoneSchema = new PhoneSchema();
        assertNotNull(phoneSchema);
    }
}
