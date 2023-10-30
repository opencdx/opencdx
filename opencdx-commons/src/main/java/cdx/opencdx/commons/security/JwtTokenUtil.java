/*
 * Copyright 2023 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.commons.security;

import static java.lang.String.format;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({"java:S1874", "java:S6437"})
public class JwtTokenUtil {

    private static final String JWT_SECRET =
            "mDRM4yJevI7le6eo3PIMM2spJD0IgGcl/Hc5jtqMNQ24oOtv3lkAbyvteyD9yepbd/6ALk0af0YxlQUFm2Lm2g==";
    private static final String JWT_ISSUER = "opencdx.cdx";
    /**
     * Default Constructor
     */
    public JwtTokenUtil() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    public String generateAccessToken(OpenCDXIAMUserModel user) {
        return Jwts.builder()
                .subject(format("%s,%s", user.getId(), user.getEmail()))
                .issuer(JWT_ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 4 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes())
                .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
