package com.codezzz.malluser.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/23 19:26
 */

@UtilityClass
public class TokenUtil {

    @Value("${token.expire}")
    private Long expire = 3600L * 1000;

    private static RSAKey rsaJWK;

    private static RSAKey rsaPublicJWK;

    private static JWSSigner signer;

    static {
        try {
            rsaJWK = new RSAKeyGenerator(2048)
                    .keyID("123")
                    .generate();
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        rsaPublicJWK = rsaJWK.toPublicJWK();

        // Create RSA-signer with the private key
        try {
            signer = new RSASSASigner(rsaJWK);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
    }

    public static String createToken(String key, String str) throws Exception {



        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("token")
                .claim(key, str)
                .expirationTime(new Date(System.currentTimeMillis() + expire))
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(),
                claimsSet);

        // Compute the RSA signature
        signedJWT.sign(signer);

        // To serialize to compact form, produces something like
        // eyJhbGciOiJSUzI1NiJ9.SW4gUlNBIHdlIHRydXN0IQ.IRMQENi4nJyp4er2L
        // mZq3ivwoAjqa1uUkSBKFIX7ATndFF5ivnt-m8uApHO4kfIFOrW7w2Ezmlg3Qd
        // maXlS9DhN0nUk_hGI3amEjkKd0BWYCB8vfUbUv0XGjQip78AI4z1PrFRNidm7
        // -jPDm5Iq0SZnjKjCNS5Q15fokXZc8u0A
        return signedJWT.serialize();
    }

    public static boolean verify(String token) {

        // On the consumer side, parse the JWS and verify its RSA signature
        SignedJWT signedJWT = SignedJWT.parse(token);

        signedJWT.

        JWSVerifier verifier = new RSASSAVerifier(rsaPublicJWK);


        Assert.True(signedJWT.verify(verifier));

        // Retrieve / verify the JWT claims according to the app requirements
        assertEquals("alice", signedJWT.getJWTClaimsSet().getSubject());
        assertEquals("https://c2id.com", signedJWT.getJWTClaimsSet().getIssuer());
        assertTrue(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()));
    }
}
