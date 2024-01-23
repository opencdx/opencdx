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
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.service.OpenCDXHtmlSanitizer;
import io.micrometer.observation.annotation.Observed;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.stereotype.Component;

/**
 * OwaspHtmlSanitizerImpl class serves to encapsulate OWASP HTML sanitizer methods.
 */
@Component
@Observed(name = "opencdx")
public class OwaspHtmlSanitizerImpl implements OpenCDXHtmlSanitizer {

    // Establish supported policy factories
    private final PolicyFactory defaultPolicy;

    /**
     * Constructs an instance of OWASP HTML Sanitizer.
     */
    public OwaspHtmlSanitizerImpl() {
        this.defaultPolicy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
    }

    /**
     * Implement the sanitize method from OpenCDXHtmlSanitizer interface.
     * @param untrustedHtml Potentially unsafe HTML string.
     * @return Sanitized HTML string.
     */
    @Override
    public String sanitize(String untrustedHtml) {
        return defaultPolicy.sanitize(untrustedHtml);
    }

}