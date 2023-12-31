package com.example.DongAisa.config;

public class SecurityConstants {
    public static final String SECRET = "your-secret-key";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/signup";
}
