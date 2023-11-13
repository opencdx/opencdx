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

import static org.mockito.Mockito.when;

import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JwtTokenUtilTest {

    JwtTokenUtil jwtTokenUtil;
    String token;
    OpenCDXIAMUserModel openCDXIAMUserModel;

    @BeforeEach
    void setUp() {
        this.jwtTokenUtil = new JwtTokenUtil();
        openCDXIAMUserModel = Mockito.mock(OpenCDXIAMUserModel.class);
        when(openCDXIAMUserModel.getUsername()).thenReturn("ab@safehealth.com");
        when(openCDXIAMUserModel.getId()).thenReturn(new ObjectId("653f1755c4203f57f39843f3"));
        token = jwtTokenUtil.generateAccessToken(openCDXIAMUserModel);
    }

    @Test
    void testGetUserId() {
        Assertions.assertEquals("653f1755c4203f57f39843f3", jwtTokenUtil.getUserId(token));
    }

    @Test
    void testGetUsername() {
        Assertions.assertEquals("ab@safehealth.com", jwtTokenUtil.getUsername(token));
    }

    @Test
    void testGetExpirationDate() {
        Assertions.assertNotNull(jwtTokenUtil.getExpirationDate(token));
    }

    @Test
    void testValidate() {
        Assertions.assertTrue(jwtTokenUtil.validate(token));
    }

    @Test
    void testValidateCatch() {
        String token1 = token + "1";
        // SignatureException
        Assertions.assertFalse(jwtTokenUtil.validate(token1));
        String token2 =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NTNmMTc1NWM0MjAzZjU3ZjM5ODQzZjMsYWJAc2FmZWhlYWx0aC5jb20iLCJpc3MiOiJvcGVuY2R4LmNkeCIsImlhdCI6MTY5ODc0MzEyNiwiZXhwIjoxNjk4NzU3NTI2fQ.F-Sc76xOCZO2ENH3valiMwwJNce3QYCoZIqjOFbCt72EDFmawAQWgIbBDjan0oj7EuK7tbSbYq20zPCVgUOxbQ";
        // ExpiredJwtException
        Assertions.assertFalse(jwtTokenUtil.validate(token2));
        // IllegalArgumentException
        Assertions.assertFalse(jwtTokenUtil.validate(null));

        String token3 =
                "eyJhbGciOiJub25lIn0.eyJzdWIiOiI2NTNmMTc1NWM0MjAzZjU3ZjM5ODQzZjMsYWJAc2FmZWhlYWx0aC5jb20iLCJpc3MiOiJvcGVuY2R4LmNkeCIsImlhdCI6MTY5ODc0MzEyNiwiZXhwIjoxNjk4NzU3NTI2fQ.";
        // UnsupportedJwtException
        Assertions.assertFalse(jwtTokenUtil.validate(token3));

        // MalformedJwtException
        String token4 =
                "eyJhbGciOiJIUUxMiJ9.eyJzdWIiOiI2NTNmMTc1NWM0MjAzZjU3ZjM5ODQzZjMsYWJAc2FmZWhlYWx0aC5b20iLCJpc3MiOiJvcGVuY2R4LmNkeCIsImlhdCI6MTY5ODc0MzEyNiwiZXhwIjoxNjk4NzU3NTI2fQ.F-Sc76xOCZO2ENH3valiMwwJNce3QYCoZIqjOFbCt72EDFmawAQWgIbBDjan0oj7EuK7tbSbYq20zPCVgUOxbQ";
        Assertions.assertFalse(jwtTokenUtil.validate(token4));
    }
}
