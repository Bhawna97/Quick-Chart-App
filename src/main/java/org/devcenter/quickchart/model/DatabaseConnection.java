package org.devcenter.quickchart.model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Class to establish the mysql database connection
 */
public class DatabaseConnection {
    public Connection databaseLink;
    public Logger logger = Logger.getAnonymousLogger();

    /**
     *function to prepare the link for establishing the database connection with a particular DB
     * @return - the DB connection link
     */
    public Connection getConnection() {
        try(InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("credentials.properties")){
            Properties properties = new Properties();
            if (input == null) {
                logger.log(Level.INFO,"Unable to find credentials.properties file.");
                return null;
            }
            properties.load(input);
            String sqlDriver = properties.getProperty("driver");
            String databaseUser = properties.getProperty("username");
            String databasePassword = properties.getProperty("password");
            String url = properties.getProperty("url");
            Class.forName(sqlDriver);
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch(Exception e){
            logger.log(Level.INFO,"An exception occurred while connecting to database.",e.getMessage());
            e.printStackTrace();
        }
        return databaseLink;
    }
}
