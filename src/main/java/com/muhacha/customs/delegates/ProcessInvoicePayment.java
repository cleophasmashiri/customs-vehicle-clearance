package com.muhacha.customs.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProcessInvoicePayment implements JavaDelegate {

    private static Logger LOGGER = LoggerFactory.getLogger(ProcessInvoicePayment.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("Process Invoice Payment");
    }
}
