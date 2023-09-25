package health.safe.api.opencdx.connected.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OpenCDXConnectedTestOrderInfo {
    /**
     *             Unique identifier for the test order
     */
    String orderId;
    /**
     *                Current status of the test order
     */
    String status;
    /**
     *          Descriptive message about the order status
     */
    String statusMessage;
    /**
     *   Actions associated with the status
     */
    List<StatusMessageAction> statusMessageActions;
    /**
     * ID of the clinical encounter associated with the test
     */
    String encounterId;
    /**
     *          Indicator if the test has been synced with an EHR system
     */
    boolean isSyncedWithEHR;
    /**
     *               Test result data
     */
    String result;
    /**
     *        ID of the associated questionnaire, if any
     */
    String questionnaireId;
}
