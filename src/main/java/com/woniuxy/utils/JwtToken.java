package com.woniuxy.utils;


import org.apache.shiro.authc.AuthenticationToken;

// token类，注意需要实现Token
public class JwtToken implements AuthenticationToken {

//    接收前端传回的token，需要在realm中support支持
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
