package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.model.BaseUser;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT创建和解析
 */
@Slf4j
public class JWTUtil {

    /**
     * token过期时间是7天
     */
    private static final long EXPIRE = 60 * 1000 * 60 * 24 * 7;

    /**
     * 加密的密钥
     */
    private static final String SECRET = "wLnB6aw9kdIl5kMsolV2uYaEeIdOLKUrLSYSpMYhgK4=";

    private static final byte[] BASE64SECRET = Decoders.BASE64.decode(SECRET);

    /**
     * subject 表示面向的用户
     */
    private static final String SUBJECT = "project";


    /**
     * 根据用户信息生成jwt令牌
     *
     * @param loginUser 登录用户
     * @return jwt
     */
    public static String generationJsonWebToken(BaseUser loginUser) {
        if (loginUser == null) {
            throw new NullPointerException("loginUser对象为空");
        }

        // 都进行一次 base64 编码
        SecretKey key = Keys.hmacShaKeyFor(BASE64SECRET);
        
        String token = Jwts
                .builder()
                .subject(SUBJECT)
                .claim("id", loginUser.getId())
                .claim("name", loginUser.getName())
                .claim("mail", loginUser.getMail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(key)
                .compact();
        return token;
    }

    /**
     * 校验token，并且获取playLoad
     *
     * @param token token
     * @return 解析的内容
     */
    public static Claims checkJWT(String token) {
        try {


            SecretKey key = Keys.hmacShaKeyFor(BASE64SECRET);
            // 解析
            JwtParser build = Jwts.parser()
                    .verifyWith(key)
                    .build();

            Claims claims = build.parseSignedClaims(token).getPayload();

            Date expiration = claims.getExpiration();
            Date now = new Date();

            if (expiration.getTime() - now.getTime() < 0) {
                log.error("Token已经过期了");
                throw new Exception("token已经过期");
            }
            return claims;
        } catch (Exception e) {
            log.error("jwt token 解析失败{}", e.getMessage());
            return null;
        }
    }


}
