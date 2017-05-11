package com.example.richardje1.brewr;

import android.util.Base64;

/**
 * Password class aquired from stackoverflow
 * Hashers passwords with getSaltedHash then checks them with the check function
 *
 * @Version 1.0
 */
public class Password {
    // The higher the number of iterations the more
    // expensive computing the hash is for us and
    // also for an attacker.
    private static final int iterations = 20*1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;

    /** Computes a salted PBKDF2 hash of given plaintext password
     suitable for storing in a database.
     Empty passwords are not supported. */
    public static String getSaltedHash(String password) throws Exception {
        byte[] salt = password.getBytes();
        // store the salt with the password
        return Base64.encodeToString(salt, Base64.DEFAULT);
    }

    /** Checks whether given plaintext password corresponds
     to a stored salted hash of the password. */
    public static boolean check(String password, String stored) throws Exception{
        byte[] salt = password.getBytes();
        String temp = Base64.encodeToString(salt, Base64.DEFAULT);
        // store the salt with the password
        return temp.equals(stored);
    }
}