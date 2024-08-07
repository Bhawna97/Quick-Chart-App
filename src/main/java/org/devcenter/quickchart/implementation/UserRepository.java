package org.devcenter.quickchart.implementation;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.devcenter.quickchart.model.DatabaseConnection;
import org.devcenter.quickchart.model.PasswordHashing;
import org.devcenter.quickchart.model.ThreeUN;
import org.devcenter.quickchart.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *Class to perform the actions for the new user or existing user
 */
public class UserRepository {
    public Logger logger = Logger.getAnonymousLogger();
    Connection connectDB;
    PreparedStatement userQuery;
    ResultSet queryResult;
    PasswordHashing ph = new PasswordHashing();
    List<ThreeUN> usernameList;

    public UserRepository(DatabaseConnection databaseConnection) {
        this.connectDB = databaseConnection.getConnection();
    }

    /**
     *Function to add user details into database and register new user
     * @param user - the object of User class to access the properties
     * @return 1 if user gets entered successfully in DB else if any sql exception occurs return 0
     */
    public int addUserIntoDB(User user){
        try{
            String hashPassword = ph.doHashing(user.getPassword());
            PreparedStatement userUpdate = connectDB.prepareStatement("INSERT INTO register_user(firstname, lastname, username, password) VALUES (?,?,?,?)");
            userUpdate.setString(1, user.getFirstname());
            userUpdate.setString(2, user.getLastname());
            userUpdate.setString(3, user.getUsername());
            userUpdate.setString(4, hashPassword);
            int executeUpdate = userUpdate.executeUpdate();
            userUpdate.close();
            return executeUpdate;
        } catch (SQLException e){
            if (e instanceof SQLIntegrityConstraintViolationException) {
                logger.log(Level.INFO, "An exception occurred while adding the user account, The Username is not unique.", e.getMessage());
                e.printStackTrace();
                return 2;
            } else {
                logger.log(Level.INFO, "An SQLException occurred while adding the user account.", e.getMessage());
                e.printStackTrace();
                return 0;
            }
        }
    }

    /**
     *Function to get user details from database and validate user
     * @param user - the object of User class to access the properties username and password
     * @return - true if user is valid/user details exist in DB and false if user is unauthorised/user details does not exist in DB
     */
    public int validateUser(User user){
        String hashPassword = ph.doHashing(user.getPassword());
        try {
            userQuery = connectDB.prepareStatement("SELECT count(1) FROM register_user WHERE username=? AND password=?");
            userQuery.setString(1, user.getUsername());
            userQuery.setString(2,hashPassword);
            queryResult = userQuery.executeQuery();
            while (queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    return queryResult.getInt(1);
                }
                if (queryResult.isLast()){
                    break;
                }
            }
            userQuery.close();
            queryResult.close();
        }catch (SQLException e){
            logger.log(Level.INFO, "An SQLException occurred while validating user account.");
            e.printStackTrace();
            e.getCause();
        }
        return 0;
    }

    /**
     * Function to check username into DB and get three unique username
     * @param user object of User class to access firstname and lastname
     * @return the list of three unique usernames
     */
    public List<ThreeUN> checkUsernameIntoDB(User user){
        try {
            Random r = new Random();
            int low = 1;
            int high = 100;
            int count = 0;
            String firstname = user.getFirstname();
            String lastname = user.getLastname();
            ThreeUN username = new ThreeUN();
            usernameList = new ArrayList<>();

            while (count!=3) {
                PreparedStatement userQuery = connectDB.prepareStatement("SELECT count(1) FROM register_user WHERE username=?");
                if(count==0){
                    String username1 = firstname + (r.nextInt(high-low) + low);
                    userQuery.setString(1, username1);
                    ResultSet queryResult = userQuery.executeQuery();
                    while (queryResult.next()) {
                        if (queryResult.getInt(1) == 0) {
                            username.setUsername1(username1);
                            count += 1;
                        }
                        if (queryResult.isLast()){
                            break;
                        }
                    }
                }else if(count==1){
                    String username2 = lastname + (r.nextInt(high-low) + low);
                    userQuery.setString(1, username2);
                    ResultSet queryResult = userQuery.executeQuery();
                    while (queryResult.next()) {
                        if (queryResult.getInt(1) == 0) {
                            username.setUsername2(username2);
                            count += 1;
                        }
                        if (queryResult.isLast()){
                            break;
                        }
                    }
                }else if(count==2){
                    String username3 = firstname + (r.nextInt(high-low) + low) + lastname.charAt(0);
                    userQuery.setString(1, username3);
                    ResultSet queryResult = userQuery.executeQuery();
                    while (queryResult.next()) {
                        if (queryResult.getInt(1) == 0) {
                            username.setUsername3(username3);
                            count += 1;
                        }
                        if (queryResult.isLast()){
                            break;
                        }
                    }
                }
            }
            usernameList.add(username);
            return usernameList;
        }catch (SQLException e){
            logger.log(Level.INFO, "An SQLException occurred while Checking the user username.", e.getMessage());
            e.printStackTrace();
        }catch (Exception e){
            logger.log(Level.INFO, "An Exception occurred while generating the user username.", e.getMessage());
            e.printStackTrace();
        }
        return usernameList;
    }
}
