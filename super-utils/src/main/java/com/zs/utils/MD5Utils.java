package com.zs.utils;

import java.security.MessageDigest;

/**
 * @Auther: zs
 * @Date: 2019/8/25 16:21
 * @Description:
 */
public class MD5Utils {

    /**
     * 异或值
     */
    private static final char XOR = 'T';

    /**
     * 盐
     */
    private static final String SALT = "恁好！";


    public static String getPassWord(String password) {
        try {
            char[] charArray = password.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                charArray[i] = (char) (charArray[i] ^ XOR);
            }
            password = String.valueOf(charArray);

            // 生成最终的加密盐
            password += SALT;

            // 确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(password.getBytes("utf-8"));
            return new String(new String(digest,"utf-8"));
        } catch (Exception var5) {
            var5.printStackTrace();
        }
        return null;
    }

}
