package com.asksword.ai.biz;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

public class JwtTest {
    @Test
    public void test() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", "12345");
        map.put("userName", "问毅");
        String sign = JWT.create()
                .withClaim("userInfo", map)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256("sword0928"));
        System.out.println(sign);
    }

    @Test
    public void testParse() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySW5mbyI6eyJ1c2VyTmFtZSI6IumXruavhSIsInVzZXJJZCI6IjEyMzQ1In0sImV4cCI6MTc2ODM1MTc3MH0.aIsNkNLLOPHBnsQ-C7_j9mQdst-aIBjuZMSVAmet9as";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("sword0928")).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        Claim userInfo = verify.getClaim("userInfo");
        System.out.println(userInfo);
    }
}
