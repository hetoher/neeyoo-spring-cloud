package com.neeyoo.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * Created by NeeYoo.
 * Created on 2019/10/29.
 * Description:
 */
public class EncryptionPasswordUtil {

    public static String encryptionPassword(String password, String salt) {
        String algorithmName = "md5";
        int hashIterations = 1024;
        SimpleHash hash = new SimpleHash(algorithmName, password, salt, hashIterations);
        return hash.toHex();
    }
}
