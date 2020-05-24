package com.muhacha.customs.delegates;

import com.muhacha.customs.service.CustomsDelegateHelper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateApplication implements JavaDelegate {

    @Autowired
    private CustomsDelegateHelper customsDelegateHelper;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if(customsDelegateHelper.validateNewApplication(delegateExecution)) {
            delegateExecution.setVariable("isApplicationValid", Boolean.TRUE);
        } else {
            delegateExecution.setVariable("isApplicationValid", Boolean.FALSE);
        }

    }
}
