package com.muhacha.customs.delegates;

import com.muhacha.customs.model.Notification;
import com.muhacha.customs.service.NotificationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyCustomerToCollectDocs implements JavaDelegate {

    private static Logger LOGGER = LoggerFactory.getLogger(NotifyCustomerToCollectDocs.class);

    @Autowired
    private NotificationService notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("Notifying Customer To Collect Docs");

        Notification message = new Notification();

        message.setToEmail((String) delegateExecution.getVariable("emailAddress"));
        message.setSubject("Customs Clearance Duty Documents Ready");
        message.setToFrom("from@muhacha.com");
        message.setToEmail(delegateExecution.getVariable("emailAddress").toString());
        message.setBody("Your customs clearance documents are ready for collection");
        message.setAction("collect-docs");
        message.setActionDescription("View Online");
        delegateExecution.setVariable("Collect-Doc-Email", message);

        notificationService.sendMessage(message);
    }
}
