package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.example.model.BaseUser;

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
    private static final String SECRET = "p-project-demo";

    /**
     * 令牌前缀
     */
    private static final String TOKEN_PREFIX = "p-project";

    /**
     * subject 表示面向的用户
     */
    private static final String SUBJECT = "project";


    /**
     * 根据用户信息生成jwt令牌
     * @param loginUser 登录用户
     * @return jwt
     */
    public static String generationJsonWebToken(BaseUser loginUser){

        if (loginUser == null){
            throw new NullPointerException("loginUser对象为空");
        }

        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", loginUser.getId())
                .claim("name", loginUser.getName())
                .claim("mail", loginUser.getMail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        token = TOKEN_PREFIX + token;
        return token;
    }

    /**
     * 校验token，并且获取playLoad
     * @param token token
     * @return 解析的内容
     */
    public static Claims checkJWT(String token){
        try {

            final Claims claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            Date expiration = claims.getExpiration();
            Date now = new Date();

            if (expiration.getTime() - now.getTime() < 0 ){
                log.error("Token已经过期了");
                throw new Exception("token已经过期");
            }
            return claims;
        }catch (Exception e){
            log.error("jwt token 解析失败{}",e.getMessage());
            return null;
        }
    }





}
