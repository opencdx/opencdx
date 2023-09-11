package health.safe.api.opencdx.commons.service.impl;

import health.safe.api.opencdx.commons.exceptions.OpenCDXUnimplemented;
import health.safe.api.opencdx.commons.handlers.OpenCDXMessageHandler;
import health.safe.api.opencdx.commons.service.OpenCDXMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

@Slf4j
public class NoOpOpenCDXMessageService implements OpenCDXMessageService {
    @Override
    public void subscribe(String subject, OpenCDXMessageHandler handler) {

    }

    @Override
    public void unSubscribe(String subject) {

    }

    @Override
    public void send(String subject, Object object) {

    }
}
