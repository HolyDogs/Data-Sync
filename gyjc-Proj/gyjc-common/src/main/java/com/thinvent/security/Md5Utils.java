package com.thinvent.security;

import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密解密
 * @author xufeng
 * @version 1.0
 * @date 2020/11/11 10:56
 **/
public class Md5Utils {

    private static String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder=new BASE64Encoder();

        String newstr=base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    public static String encodePassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String str=password+"xufeng";
        return encoderByMd5(encoderByMd5(encoderByMd5(str)));
    }

    public static String base64Encode(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        //base64编码
        return new BASE64Encoder().encode(str.getBytes());
    }

    public static String base64Decode(String str) throws IOException {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        byte [] byteArr = new BASE64Decoder().decodeBuffer(str);
        return new String(byteArr);
    }
}
