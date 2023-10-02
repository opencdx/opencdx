package health.safe.api.opencdx.commons.service;

/**
 * HTML Sanitizer to use for validating HTML being sent in.
 */
public interface OpenCDXHtmlSanitizer {
    /**
     * Method to sanitize HTML
     * @param html String containing the HTML
     * @return String containing the sanitized HTML.
     */
    String sanitize(String html);
}
