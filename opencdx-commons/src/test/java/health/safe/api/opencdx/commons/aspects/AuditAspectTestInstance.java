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

    @OpenCDXAuditUser(actor = "attributes.actor",patient = "attributes.patient")
    public String testAnnotationChild(RequestActorAttributes attributes) {
        this.info = AuditAspect.getCurrentThreadInfo();
        return String.format("Say Hello to %s and %s!",attributes.getActor(),attributes.getPatient());
    }

    @OpenCDXAuditUser(actor = "attributes.actor",patient = "patient")
    public String testAnnotationFail(RequestActorAttributes attributes) {
        this.info = AuditAspect.getCurrentThreadInfo();
        return String.format("Say Hello to %s and %s!",attributes.getActor(),attributes.getPatient());
    }

    @OpenCDXAuditUser(actor = "actor",patient = "attributes.patient")
    public String testAnnotationFail2(RequestActorAttributes attributes) {
        this.info = AuditAspect.getCurrentThreadInfo();
        return String.format("Say Hello to %s and %s!",attributes.getActor(),attributes.getPatient());
    }
}
