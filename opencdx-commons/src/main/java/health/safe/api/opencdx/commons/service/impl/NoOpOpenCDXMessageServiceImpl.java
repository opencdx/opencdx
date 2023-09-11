package health.safe.api.opencdx.commons.service.impl;

import health.safe.api.opencdx.commons.handlers.OpenCDXMessageHandler;
import health.safe.api.opencdx.commons.service.OpenCDXMessageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoOpOpenCDXMessageServiceImpl implements OpenCDXMessageService {
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
