package com.zerobase.reservationdiner.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {

    public static boolean equals(String plainPassword,String hashed){
        if(plainPassword==null||plainPassword.length()<1){
            return  false;
        }

        if(hashed==null||hashed.length()<1){
            return false;
        }

        return BCrypt.checkpw(plainPassword,hashed);
    }
}
