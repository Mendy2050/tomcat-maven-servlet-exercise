package com.itheima.web.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.spec.EncodedKeySpec;

/**
 * @author Mendy
 * @create 2023-05-25 8:18
 */
public class URLDemo {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String username = "张三";

        String encode = URLEncoder.encode(username, "utf-8");
        System.out.println(encode);

        String decode = URLDecoder.decode(encode, "ISO-8859-1");
        System.out.println(decode);

        byte[] bytes = decode.getBytes("ISO-8859-1");
        String s = new String(bytes, "utf-8");

        System.out.println(s);


    }
}
