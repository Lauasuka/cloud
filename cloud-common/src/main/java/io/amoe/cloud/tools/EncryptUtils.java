package io.amoe.cloud.tools;

import io.amoe.cloud.enums.BizResponseStatus;
import io.amoe.cloud.exception.BizException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Amoe
 * @date 2020/5/26 14:46
 */
public final class EncryptUtils {
    private static final String ALGORITHM_MD5 = "md5";
    private static final int DEFAULT_LEN = 12;
    private static final int BIT_32 = 32;
    private static final int BIT_64 = 64;

    private EncryptUtils() {}

    /**
     * Get default length salt
     *
     * @return salt
     */
    public static String getSalt() {
        return getSalt(DEFAULT_LEN);
    }

    /**
     * Get the salt of the specified length
     * if length <= 0,will get the default length
     *
     * @param length salt length
     * @return salt
     */
    public static String getSalt(int length) {
        if (length <= 0) {
            return RandomStringUtils.randomAlphabetic(DEFAULT_LEN);
        }
        return RandomStringUtils.randomAlphabetic(length);
    }

    /**
     * Generate md5 password
     *
     * @param rawPwd raw password
     * @param salt   salt
     * @return encode password
     */
    public static String genMd5Pwd(String rawPwd, String salt) {
        if (StringUtils.isBlank(rawPwd)) {
            throw new IllegalArgumentException("Raw password must not be blank");
        }
        String mixinPwd = rawPwd + salt;
        return md5(md5(mixinPwd));
    }

    /**
     * Match the raw password and encode password
     *
     * @param rawPwd raw password
     * @param salt salt
     * @param encodePwd encode pwd
     * @return match result
     */
    public static Boolean matchMd5Pwd(String rawPwd, String salt, String encodePwd) {
        if (StringUtils.isBlank(rawPwd)) {
            throw new IllegalArgumentException("Raw password must not be blank");
        }
        if (StringUtils.isBlank(encodePwd)) {
            throw new IllegalArgumentException("Encode password must not be blank");
        }
        String matchPwd = md5(md5(rawPwd + salt));
        return matchPwd.equals(encodePwd);
    }

    private static String md5(String raw) {
        if (StringUtils.isBlank(raw)) {
            throw new IllegalArgumentException("Raw text must not be blank");
        }
        byte[] secretBytes;
        try {
            secretBytes = MessageDigest.getInstance(ALGORITHM_MD5).digest(raw.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new BizException(BizResponseStatus.ERROR);
        }
        // 16进制数字
        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < BIT_32 - md5code.length(); i++) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }
}
