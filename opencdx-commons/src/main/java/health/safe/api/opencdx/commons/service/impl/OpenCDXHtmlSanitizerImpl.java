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
package health.safe.api.opencdx.commons.service.impl;

import health.safe.api.opencdx.commons.service.OpenCDXHtmlSanitizer;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.stereotype.Component;

/**
 * THis class is intended to supply OWASP HTML sanitizer methods
 */
@Component
public class OpenCDXHtmlSanitizerImpl implements OpenCDXHtmlSanitizer {

    // Define supported policy factories
    private PolicyFactory defaultPolicy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    public OpenCDXHtmlSanitizerImpl() {
        this.defaultPolicy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
    }

    public String sanitize(String untrustedHtml) {
        return defaultPolicy.sanitize(untrustedHtml);
    }
}
