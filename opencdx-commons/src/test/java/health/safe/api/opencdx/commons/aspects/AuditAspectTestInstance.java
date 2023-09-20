package health.safe.api.opencdx.commons.aspects;

import health.safe.api.opencdx.commons.annotations.OpenCDXAuditUser;
import health.safe.api.opencdx.commons.dto.RequestActorAttributes;

public class AuditAspectTestInstance {

    public RequestActorAttributes info;

    public RequestActorAttributes getInfo() {
        return info;
    }

    @OpenCDXAuditUser(actor = "actor",patient = "patient")
    public String testAnnotation(String actor, String patient) {
        this.info = AuditAspect.getCurrentThreadInfo();
        return String.format("Say Hello to %s and %s!",actor,patient);
    }
}
