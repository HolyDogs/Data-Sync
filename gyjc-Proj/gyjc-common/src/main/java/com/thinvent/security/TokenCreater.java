package com.thinvent.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

/**
 * token创建以及解锁
 * @author xufeng
 * @version 1.0
 * @date 2020/11/11 10:56
 **/
public class TokenCreater {

    public static String createJWT(String id, String subject, long ttlMillis){
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("gyjcProj7788");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                //jwt的签发时间
                .setIssuedAt(now)
                //用于表示jwt面向的用户标识
                .setSubject(subject)
                //jwt签发者
                .setIssuer("gyjc")
                //签名算法以及签名密钥
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    /**
     * 解密
     * @param jwt
     */
    public static HashMap parseJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)


        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("gyjcProj7788"))
                .parseClaimsJws(jwt).getBody();

        HashMap<String,Object> map = new HashMap<>(16);

        map.put("id",claims.getId());
        map.put("name",claims.getSubject());
        //生效时间
        map.put("iat", claims.getIssuedAt());
        //过期时间
        map.put("exp", claims.getExpiration());
        return map;
    }
}
