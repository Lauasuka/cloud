package io.amoe.cloud.tools;

import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Amoe
 * @date 2020/5/26 14:46
 */
public final class EncryptUtils {
    private static final String ALGORITHM_MD5 = "md5";
    private static final int DEFAULT_LEN = 12;

    public static String getSalt() {
        return getSalt(DEFAULT_LEN);
    }

    public static String getSalt(int length) {
        if (length <= 0) {
            return RandomStringUtils.random(12);
        }
        return RandomStringUtils.random(length);
    }

    public static String getEncryptPassword(String sourcePwd, String salt) {
        String encryptStr = sourcePwd + salt;
        return md5(encryptStr);
    }

    private static String md5(String text) {
        byte[] secretBytes;
        try {
            secretBytes = MessageDigest.getInstance(ALGORITHM_MD5).digest(
                    text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Not these algorithm");
        }
        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }

}
