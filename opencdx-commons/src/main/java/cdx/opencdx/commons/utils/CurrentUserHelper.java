/*
 * Copyright 2024 Safe Health Systems, Inc.
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
package cdx.opencdx.commons.utils;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import org.springframework.stereotype.Component;

/**
 * Helper class to get the current user.
 */
@Component
@ExcludeFromJacocoGeneratedReport
public class CurrentUserHelper {

    private static OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor for CurrentUserHelper.
     * @param openCDXCurrentUser Current user.
     */
    @SuppressWarnings({"java:S1118", "java:S3010"})
    public CurrentUserHelper(OpenCDXCurrentUser openCDXCurrentUser) {
        CurrentUserHelper.openCDXCurrentUser = openCDXCurrentUser;
    }

    /**
     * Get the current user.
     * @return Current user.
     */
    public static OpenCDXCurrentUser getOpenCDXCurrentUser() {
        return CurrentUserHelper.openCDXCurrentUser;
    }
}
