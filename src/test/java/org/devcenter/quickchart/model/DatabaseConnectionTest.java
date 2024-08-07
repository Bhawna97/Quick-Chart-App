package org.devcenter.quickchart.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class DatabaseConnectionTest {

    @Mock
    private DatabaseConnection databaseConnection = Mockito.mock(DatabaseConnection.class);

    @Mock
    private Connection mockConnection = Mockito.mock(Connection.class);

    @BeforeEach
    public void setUp() throws IOException {
        assertNotNull(databaseConnection);
        when(databaseConnection.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void getConnection() throws IOException {
        assertEquals(databaseConnection.getConnection(), mockConnection);
    }
}