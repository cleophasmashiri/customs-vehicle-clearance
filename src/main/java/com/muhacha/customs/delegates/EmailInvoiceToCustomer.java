package com.muhacha.customs.delegates;

import com.muhacha.customs.model.Notification;
import com.muhacha.customs.service.NotificationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailInvoiceToCustomer implements JavaDelegate {

    @Autowired
    private NotificationService notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Notification message = new Notification();

        message.setToEmail((String) delegateExecution.getVariable("emailAddress"));
        message.setSubject("Customs Clearance Duty Invoice");
        message.setToFrom("from@muhacha.com");
        message.setToEmail(delegateExecution.getVariable("emailAddress").toString());
        message.setBody("Your customs clearance duty invoice has been created");
        message.setAction("pay-invoice-online");
        message.setActionDescription("Pay Invoice online");
        delegateExecution.setVariable("Invoice-Email", message);

        notificationService.sendMessage(message);
    }
}
