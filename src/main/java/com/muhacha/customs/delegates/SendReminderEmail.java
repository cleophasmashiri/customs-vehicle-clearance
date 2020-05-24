package com.muhacha.customs.delegates;

import com.muhacha.customs.model.Notification;
import com.muhacha.customs.service.NotificationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendReminderEmail implements JavaDelegate {

    @Autowired
    private NotificationService notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Notification message = new Notification();

        message.setToEmail((String) delegateExecution.getVariable("emailAddress"));
        message.setSubject("Clearance Application Overdue");
        message.setToFrom("from@muhacha.com");
        message.setToEmail(delegateExecution.getVariable("emailAddress").toString());
        message.setBody("You are requested to attend to an overdue clearance application");
        message.setAction("clearance-application");
        message.setActionDescription("Process Clearance Application ");
        delegateExecution.setVariable("ClearanceApplicationOverdueEmail", message);

        notificationService.sendMessage(message);
    }
}
