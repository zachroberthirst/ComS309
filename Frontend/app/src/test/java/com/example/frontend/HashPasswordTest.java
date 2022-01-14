package com.example.frontend;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.example.frontend.model.helpers.HashPassword;

import org.junit.*;


public class HashPasswordTest{

    @Test
    public void testHash(){
        String plainText = "password";
        String salt = "salt";
        String hash1 = HashPassword.hash(plainText, salt);
        assertEquals("test hash is determinant: ", HashPassword.hash(plainText, salt), hash1);
        assertEquals("test hash is determinant: ", HashPassword.hash(plainText, salt), hash1);
        assertEquals("test hash is determinant: ", HashPassword.hash(plainText, salt), hash1);
        assertEquals("test hash is determinant: ", HashPassword.hash(plainText, salt), hash1);

        for(int i =0; i<50; i++){
            assertNotEquals("test hash hashes: ", HashPassword.hash(plainText, HashPassword.newSalt()), plainText);
        }
        assertEquals("null password: ", HashPassword.hash(null, salt), null);
        assertEquals("null salt: ", HashPassword.hash(plainText, null), null);
        assertEquals("null password and salt: ", HashPassword.hash(null, null), null);

    }

    @Test
    public void testVerify(){
        String plainText = "password";
        String salt = "salt";
        String hash1 = HashPassword.hash(plainText, salt);
        assertTrue("verify correctly check that hashes are equal: ", HashPassword.verify(plainText, salt, hash1));
        assertTrue("verify correctly check that hashes are equal: ", HashPassword.verify(plainText, salt, hash1));
        assertTrue("verify correctly check that hashes are equal: ", HashPassword.verify(plainText, salt, hash1));
        for(int i = 0; i<50; i++){
            String password = "password";
            String wrongPassword = "Password";
            String newSalt = HashPassword.newSalt();
            String hash2 = HashPassword.hash(password, newSalt);
            assertTrue("verify correctly check that hashes are equal: ", HashPassword.verify(password, newSalt, hash2));
            assertFalse("verify correctly check that hashes are notEqual: ", HashPassword.verify(wrongPassword, newSalt, hash2));
        }
        assertFalse("null password: ", HashPassword.verify(null, salt, hash1));
        assertFalse("null hash: ", HashPassword.verify(plainText, salt, null));
        assertFalse("null salt: ", HashPassword.verify(plainText, null, hash1));
        assertFalse("null salt and password: ", HashPassword.verify(null, null, hash1));
        assertFalse("null  and hash: ", HashPassword.verify(plainText, null, null));
        assertFalse("null password and hash: ", HashPassword.verify(null, salt, null));
        assertFalse("null password and hash and salt: ", HashPassword.verify(null, null, null));
    }
}
