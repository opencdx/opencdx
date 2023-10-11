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

    public OpenCDXHtmlSanitizerImpl() {}

    public String sanitize(String untrustedHtml) {
        return defaultPolicy.sanitize(untrustedHtml);
    }
}
