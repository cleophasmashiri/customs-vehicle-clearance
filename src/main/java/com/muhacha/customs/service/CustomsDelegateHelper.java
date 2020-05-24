package com.muhacha.customs.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
public class CustomsDelegateHelper {
    public boolean validateNewApplication(DelegateExecution delegateExecution) {
        if(!isDefined("firstName", delegateExecution)
                || !isDefined("lastName", delegateExecution) ||
                !isDefined("emailAddress", delegateExecution)
//                || !isDefined("phone", delegateExecution)
        || !isDefined("vehicleMake", delegateExecution)) {
            return false;
        }
        return true;
    }

    private boolean isDefined(String fieldName, DelegateExecution delegateExecution) {
        if(delegateExecution.getVariable(fieldName) == null || "".equals(delegateExecution.getVariable(fieldName))) {
            return false;
        }
        return true;
    }
}
