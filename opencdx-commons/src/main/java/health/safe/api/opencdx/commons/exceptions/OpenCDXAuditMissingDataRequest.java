package health.safe.api.opencdx.commons.exceptions;

import com.google.rpc.Code;
import org.springframework.http.HttpStatus;

/**
 * OpenCDXAuditMissingDataRequest occurs when current operation is missing data.
 *  <p>
 *  Https Status: NO_CONTENT
 *  <p>
 *  Google Code: NOT_FOUND
 **/
public class OpenCDXAuditMissingDataRequest extends OpenCDXException {

    /**
     * Constructor for Exception.
     *
     * @param status  HttpStatus this exception should return
     * @param code    Google Code this exception should return
     * @param domain  Class Name exception created in
     * @param number  Integer representing specific instance of Exception in class. Should be unique.
     * @param message Message indicating the failure reason.
     */
    protected OpenCDXAuditMissingDataRequest(HttpStatus status, Code code, String domain, int number, String message) {
        super(status, code, domain, number, message);
    }

    /**
     * Constructor for Exception.
     *
     * @param status  HttpStatus this exception should return
     * @param code    Google Code this exception should return
     * @param domain  Class Name exception created in
     * @param number  Integer representing specific instance of Exception in class. Should be unique.
     * @param message Message indicating the failure reason.
     * @param cause   Throwable that was the cause of this exception.
     */
    protected OpenCDXAuditMissingDataRequest(HttpStatus status, Code code, String domain, int number, String message, Throwable cause) {
        super(status, code, domain, number, message, cause);
    }

    /**
     * Constructor for Exception.
     *
     * @param status             HttpStatus this exception should return
     * @param code               Google Code this exception should return
     * @param domain             Class Name exception created in
     * @param number             Integer representing specific instance of Exception in class. Should be unique.
     * @param message            Message indicating the failure reason.
     * @param cause              Throwable that was the cause of this exception.
     * @param enableSuppression  Boolean to indicate allow suppression.
     * @param writableStackTrace Boolean to indicate writeable stack trace.
     */
    protected OpenCDXAuditMissingDataRequest(HttpStatus status, Code code, String domain, int number, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(status, code, domain, number, message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Constructor for Exception.
     * @param domain Class Name exception created in
     * @param number Integer representing specific instance of Exception in class. Should be unique.
     * @param message Message indicating the failure reason.
     * @param cause Throwable that was the cause of this exception.
     * @param enableSuppression Boolean to indicate allow suppression.
     * @param writableStackTrace Boolean to indicate writeable stack trace.
     */
    public OpenCDXAuditMissingDataRequest(
            String domain,
            int number,
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(
                HttpStatus.NO_CONTENT,
                Code.NOT_FOUND,
                domain,
                number,
                message,
                cause,
                enableSuppression,
                writableStackTrace);
    }
}
