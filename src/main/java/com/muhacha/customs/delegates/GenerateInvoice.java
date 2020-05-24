package com.muhacha.customs.delegates;

import com.muhacha.customs.model.InvoiceBase;
import com.muhacha.customs.service.InvoiceService;
import com.muhacha.customs.service.PdfGeneratorUtil;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GenerateInvoice implements JavaDelegate {

    @Autowired
    private PdfGeneratorUtil pdfGenaratorUtil;

    @Autowired
    private InvoiceService invoiceService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        InvoiceBase invoice = invoiceService.generateNew(delegateExecution);

        pdfGenaratorUtil.createPdf("quotation", invoice, delegateExecution, "Invoice");
        delegateExecution.setVariable("invoice", invoice);

    }
}
