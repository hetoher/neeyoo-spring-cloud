package com.neeyoo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.neeyoo.config.Constant;

import java.util.Date;

/**
 * Created by NeeYoo.
 * Created on 2019/10/29.
 * Description:
 */
public class JwtTokenUtil {

    /**
     * Create by NeeYoo.
     * Create on 2019/10/29.
     * Description: 生成token
     */
    public static String generateToken(String userPhone, String deviceId, String secretKey, Long tokenExpireTime) {
        //生成jwt
        Date now = new Date();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + tokenExpireTime))
                .withClaim(Constant.USER_PHONE, userPhone)//保存身份标识
                .withClaim(Constant.DEVICE_ID, deviceId)//保存身份标识
                .sign(algorithm);
        return token;
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/10/29.
     * Description: 解析token
     */
    public static DecodedJWT decodedJWT(Algorithm algorithm, String token) throws Exception {
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        return verifier.verify(token);
    }
}
