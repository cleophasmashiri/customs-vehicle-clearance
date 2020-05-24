package com.muhacha.customs.delegates;

import com.muhacha.customs.model.Notification;
import com.muhacha.customs.service.NotificationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailQuoteToCustomer implements JavaDelegate {

    @Autowired
    private NotificationService notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Notification message = new Notification();

        message.setToEmail((String) delegateExecution.getVariable("emailAddress"));
        message.setSubject("Customs Clearance Duty Quotation");
        message.setToFrom("from@muhacha.com");
        message.setToEmail(delegateExecution.getVariable("emailAddress").toString());
        message.setBody("Your customs clearance duty quotation has been created");
        message.setAction("view-quote-online");
        message.setActionDescription("View Quote online");
        delegateExecution.setVariable("Quotation-Email", message);

        notificationService.sendMessage(message);
    }
}
