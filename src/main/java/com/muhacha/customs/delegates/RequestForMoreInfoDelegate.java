package com.muhacha.customs.delegates;

import com.muhacha.customs.model.Notification;
import com.muhacha.customs.service.NotificationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestForMoreInfoDelegate implements JavaDelegate {

    @Autowired
    private NotificationService notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Notification message = new Notification();

        message.setToEmail((String) delegateExecution.getVariable("emailAddress"));
        message.setSubject("Courtesy Notification");
        message.setToFrom("from@muhacha.com");
        message.setToEmail(delegateExecution.getVariable("emailAddress").toString());
        message.setBody("You are requested to provide more information as indicated in the link below");
        message.setAction("more-infor");
        message.setActionDescription("Some information missing");
        delegateExecution.setVariable("requestForMoreInfo", message);

        notificationService.sendMessage(message);
    }
}
