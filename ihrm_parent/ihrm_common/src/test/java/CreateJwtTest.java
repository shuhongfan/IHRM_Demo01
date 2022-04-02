import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

/**
 * 通过jjwt创建token
 */
public class CreateJwtTest {
    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("88")
                .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "shuhongfan")
                .claim("companyId", "123456")
                .claim("companyName", "湖北武汉股份有限公司");

        String token = jwtBuilder.compact();
        System.out.println(token);
    }
}
