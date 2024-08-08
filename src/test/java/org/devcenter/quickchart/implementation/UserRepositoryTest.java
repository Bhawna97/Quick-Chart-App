package org.devcenter.quickchart.implementation;

import org.devcenter.quickchart.model.DatabaseConnection;
import org.devcenter.quickchart.model.ThreeUN;
import org.devcenter.quickchart.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class UserRepositoryTest {

    @Mock
    private DatabaseConnection databaseConnection = Mockito.mock(DatabaseConnection.class);

    @Mock
    private Connection mockConnection = Mockito.mock(Connection.class);

    @Mock
    private PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);

    @Mock
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    @Mock
    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    UserRepository userRepo;
    User user;
    ThreeUN userNames;

    @BeforeEach
    public void setUp() throws Exception {
        assertNotNull(databaseConnection);
        when(databaseConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(resultSet);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.isLast()).thenReturn(true);
        userRepo = new UserRepository(databaseConnection);

        user = new User();
        user.setFirstname("Bhawna");
        user.setLastname("Badlani");
        user.setUsername("Bhawna56");
        user.setPassword("Bhawna@56");
    }

    @Test
    void addUserIntoDB() throws SQLException {
        assertEquals(userRepo.addUserIntoDB(user), 1);
        when(mockStatement.executeUpdate()).thenReturn(0);
        assertEquals(userRepo.addUserIntoDB(user), 0);
    }

    @Test
    void invalidUsername() throws SQLException {
        when(mockStatement.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        assertEquals(userRepo.addUserIntoDB(user), 2);
    }

    @Test
    void validateUser() throws SQLException {
        when(resultSet.getInt(anyInt())).thenReturn(1);
        assertEquals(userRepo.validateUser(user), 1);
    }

    @Test
    void unauthorizedUser() throws SQLException {
        when(resultSet.getInt(anyInt())).thenReturn(0);
        assertEquals(userRepo.validateUser(user), 0);
    }

    @Test
    void checkUsernameIntoDB() {
        List<ThreeUN> usernameList = new ArrayList<>();
        userNames = new ThreeUN();
        userNames.setUsername1("Bhawna56");
        userNames.setUsername2("Badlani71");
        userNames.setUsername3("Bhawna42B");
        usernameList.add(userNames);
        when(userRepository.checkUsernameIntoDB(user)).thenReturn(usernameList);
        assertEquals(userRepository.checkUsernameIntoDB(user), usernameList);
    }
}