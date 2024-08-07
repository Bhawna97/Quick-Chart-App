package org.devcenter.quickchart.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHashingTest {

    @Test
    void doHashing() {
        PasswordHashing PHashing = new PasswordHashing();
        final String hashCode = "098f6bcd4621d373cade4e832627b4f6";
        assertEquals(hashCode,PHashing.doHashing("test"));
    }

}