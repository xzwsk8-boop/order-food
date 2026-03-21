package weixin.order_food.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    // 密钥，实际项目中建议放入配置文件，长度至少需要32个字符
    private static final String SECRET_KEY = "YourSuperSecretKeyForWeChatAppWhichIsLongEnough";
    
    // 过期时间：7天 (毫秒)
    private static final long EXPIRATION_TIME = 7L * 24 * 60 * 60 * 1000;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 根据 userId 和 openid 生成 JWT
     */
    public String generateToken(Long userId, String openid) {
        return Jwts.builder()
                .subject(openid)
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 解析 JWT，获取 Claims
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 验证 Token 是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从 Token 中获取 userId
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("userId", Long.class);
    }
    
    /**
     * 从 Token 中获取 openid
     */
    public String getOpenidFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }
}
