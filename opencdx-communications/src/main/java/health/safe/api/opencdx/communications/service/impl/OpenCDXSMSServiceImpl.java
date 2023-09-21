package health.safe.api.opencdx.communications.service.impl;

import health.safe.api.opencdx.communications.service.OpenCDXSMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OpenCDXSMSServiceImpl implements OpenCDXSMSService {
    /**
     * Method to send SMS Notificaiton
     *
     * @return boolean indicating if successful.
     */
    @Override
    public boolean sendSMS(String message, List<String> phoneNumbers) {
        log.info("Message has been sent. Message :\n {} , PhoneNumbers :\n {}", message, phoneNumbers);
        return true;
    }
}
