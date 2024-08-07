package org.devcenter.quickchart.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordHashing {
    public Logger logger = Logger.getAnonymousLogger();
    /**
     *function to generate the hash code for password
     * @param password - the string for which hashcode need to be generated
     * @return - the hashcode string for password
     */
    public String doHashing(String password){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] resultByteArray = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for(byte b : resultByteArray){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            logger.log(Level.INFO, "An Exception occurred while implementing the hashing algorithm.", e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
}
