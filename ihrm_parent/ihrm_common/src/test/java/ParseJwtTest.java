import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

/**
 * 解析JWT字符串
 */

public class ParseJwtTest {
    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4OCIsInN1YiI6IuWwj-eZvSIsImlhdCI6MTY0ODg4MTU0MywiY29tcGFueUlkIjoiMTIzNDU2IiwiY29tcGFueU5hbWUiOiLmuZbljJfmrabmsYnogqHku73mnInpmZDlhazlj7gifQ.VY1CxhkhvrG5_Iq6NAHUHN3rezFSoykwqRZOVBQoQNY";

        Claims claims = Jwts.parser()
                .setSigningKey("shuhongfan")
                .parseClaimsJws(token)
                .getBody();

        String id = claims.getId();
        String subject = claims.getSubject();
        Date date = claims.getIssuedAt();

        System.out.println(id);
        System.out.println(subject);
        System.out.println(date);

//        解析自定义claim的内容
        String companyId = (String) claims.get("companyId");
        String companyName = (String) claims.get("companyName");

        System.out.println(companyId+"======"+companyName);
    }
}
