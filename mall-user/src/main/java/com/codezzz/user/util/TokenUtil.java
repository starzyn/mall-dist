package com.codezzz.user.util;

import com.codezzz.core.exception.ErrorCode;
import com.codezzz.core.exception.MallException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/23 19:26
 */

@UtilityClass
@Slf4j
public class TokenUtil {

    @Value("${token.expire}")
    private Long expire = 1000L;

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

    public static String createToken(String key, String str){

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
        try {
            signedJWT.sign(signer);
        } catch (Exception e) {
            log.error("签名失败：{}", e);
            throw new MallException(ErrorCode.TOKEN_GENERATE_FAILED);
        }

        return signedJWT.serialize();

    }

    public static boolean verify(String token) {
        try {

            // On the consumer side, parse the JWS and verify its RSA signature
            SignedJWT signedJWT = SignedJWT.parse(token);


            JWSVerifier verifier = new RSASSAVerifier(rsaPublicJWK);

            boolean res = signedJWT.verify(verifier);

            res = signedJWT.getJWTClaimsSet().getExpirationTime().after(new Date(System.currentTimeMillis()));

            return res;

        } catch (Exception e) {
            log.error("token 校验失败: {}", e);
            throw new MallException(ErrorCode.TOKEN_VALID);
        }

    }


    public static Object valueOf(String key, String token) {
        try {

            // On the consumer side, parse the JWS and verify its RSA signature
            SignedJWT signedJWT = SignedJWT.parse(token);
            if (signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date(System.currentTimeMillis()))) {
                return null;
            }
            JWTClaimsSet set = signedJWT.getJWTClaimsSet();
            return set.getClaim(key);
        } catch (Exception e) {
            System.out.println(e);
            throw new MallException(ErrorCode.TOKEN_PARSE_FAILED);
        }
    }

    /**
     * 使用gzip压缩字符串
     * @param str 要压缩的字符串
     * @return
     */
    public static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new sun.misc.BASE64Encoder().encode(out.toByteArray());
    }

    /**
     * 使用gzip解压缩
     * @param compressedStr 压缩字符串
     * @return
     */
    public static String uncompress(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return decompressed;
    }


    public static void main(String[] args) throws Exception {
//        User user = new User();
//        user.setId("111");
//        user.setNickname("__父亲__");
//        user.setUsername("starzyn");
//        user.setPhoneNumber("18339182210");
//        user.setPassword("root");
//        String token = TokenUtil.createToken("token", JsonMapper.toAlwaysJson(user));
//        TimeUnit.SECONDS.sleep(3);
//        System.out.println(token);
//        System.out.println(TokenUtil.verify(token));
//        System.out.println(TokenUtil.valueOf("token", token));

        String text = "eyJraWQiOiIxMjMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsImV4cCI6MTYyOTc3MzM4MCwidG9rZW4iOiJ7XCJpZFwiOlwiMTExXCIsXCJ1c2VybmFtZVwiOlwic3Rhcnp5blwiLFwicGFzc3dvcmRcIjpcInJvb3RcIixcIm5pY2tuYW1lXCI6XCJfX-eItuS6sl9fXCIsXCJ0eXBlXCI6bnVsbCxcImNyZWF0ZWRBdFwiOm51bGwsXCJzdGF0dXNcIjpudWxsLFwicGhvbmVOdW1iZXJcIjpcIjE4MzM5MTgyMjEwXCJ9In0.rQmmvl8ZZFUVd-tt-8OgDRRcBA8M9jwWP_r8FrdmhzxS9OxRkS52_uW1Fl5eH9G_mYpgK97RoW6HGngnr738eeaGkNJ9QOKpXvOGgDapY7jTvLrOjdL8zbMZUkW07Rzcivhnb3UB7te75gKeuR026a__NncLDHAkjrMCrhxAW_eyTU9Vbt_lRXqV6EBdfThp1YCnmTgkC8fhImDDOUWxY3OoSdaQhiaAOqz8jsc5oJ_nHoDLPhFuob_QMV_n2oEtRIHH3kJ0mlwlhJtGb9vcwtPHNS1P26B4YYHkpozRyxTMd7bIFD-OvuQKhXkuWZXd8j4qebmOEpvW88Xbk1yDpg";
        String zip = compress(text);
        System.out.println("压缩后：\n" + zip + "长度：" + zip.length());
        System.out.println("解压缩后：\n" + uncompress(zip) + "长度：" + uncompress(zip).length());
    }
}
