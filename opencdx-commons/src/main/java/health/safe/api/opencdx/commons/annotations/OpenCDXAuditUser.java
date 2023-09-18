package health.safe.api.opencdx.commons.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

/**
 * Annotation to exclude the class or method from being calculated into
 * the JaCoCo coverage report.  Should only be used sparingly.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface OpenCDXAuditUser {

    UUID actor = null;
    UUID patient = null;
}