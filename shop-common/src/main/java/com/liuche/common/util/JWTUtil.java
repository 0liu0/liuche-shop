package com.liuche.common.util;

import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.CommonUtil;
import com.liuche.common.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author 刘彻
 * @Date 2023/7/27 15:03
 * @PackageName: com.liuche.user.util
 * @ClassName: JWTUtil
 * @Description: jwt的工具类
 */
@Slf4j
public class JWTUtil {
    private static final String TOKEN_PREFIX = "liuche-shop"; // 前缀
    private static final String SUBJECT = "login-user-info"; // 主题
    private static final String SECRET = "liuche-shop-salt"; // 加密的盐
    private static final Long EXPIRE = 1000 * 60 * 60 * 24 * 2L; // 保存时效两天
    /**
     * 根据用户信息，生成令牌
     * 当前token:liuche-shopeyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbi11c2VyLWluZm8iLCJpcCI6IjEyNy4wLjAuMSIsImlkIjo1LCJuYW1lIjoibGl1Y2hlIiwibWFpbCI6IjcwNjcxNjg1MkBxcS5jb20iLCJleHAiOjE2OTA2MTk5MzF9.FWrLjqGFJs5JX96nzosRAqUjdA-yyRxkAiiIIqv_AlU
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user, HttpServletRequest request) {
        // 得到用户的请求地址
        String token = null;
        try {
            token = Jwts.builder().setSubject(SUBJECT)
                    .claim("ip", CommonUtil.getRemoteIp(request))
                    .claim("id", user.getId())
                    .claim("name", user.getName())
                    .claim("mail", user.getMail())
//                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                    .signWith(SignatureAlgorithm.HS256, SECRET)
//                    .compressWith(CompressionCodecs.GZIP)
                    .compact();
        } catch (Exception e) {
            log.info("e:"+e);
            throw new BusinessException(ExceptionCode.SYSTEM_ERROR,"生成token失败");
        }
//        String token = "hfqiwueohfdoieqrfhuoiqew2rhf";
        token = TOKEN_PREFIX + token;
        log.info("token:" + token);
        return token;
    }

    /**
     * 校验token的方法
     *
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
