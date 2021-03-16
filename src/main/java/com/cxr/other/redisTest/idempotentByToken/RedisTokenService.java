package com.cxr.other.redisTest.idempotentByToken;



public interface RedisTokenService {

    //生成Token
    public String getToken();

    //删除Token
    public Boolean deleteToken(String token);

}

