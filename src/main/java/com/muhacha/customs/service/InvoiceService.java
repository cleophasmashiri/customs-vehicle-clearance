package com.muhacha.customs.service;

import com.muhacha.customs.model.InvoiceBase;
import com.muhacha.customs.model.Quotation;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
public class InvoiceService {

    public InvoiceBase generateNew(DelegateExecution delegateExecution) {
        Quotation quotation = new Quotation();
        quotation.setAddressline1(delegateExecution.getVariable("addressline1").toString());
        quotation.setAddressCity(delegateExecution.getVariable("addressCity").toString());
        quotation.setAddressState(delegateExecution.getVariable("addressState").toString());
        quotation.setAddressPostalCode(delegateExecution.getVariable("addressPostalCode").toString());
        quotation.addLineItem("Duty Amount", 1, 1000.0);
        quotation.addLineItem("Temporary Number Plates", 1, 200.0);
       return quotation;
    }


}
