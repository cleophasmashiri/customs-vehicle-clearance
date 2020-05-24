package com.muhacha.customs.delegates;

import com.muhacha.customs.model.InvoiceBase;
import com.muhacha.customs.service.InvoiceService;
import com.muhacha.customs.service.PdfGeneratorUtil;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GenerateQuote implements JavaDelegate {

    @Autowired
    private PdfGeneratorUtil pdfGenaratorUtil;

    @Autowired
    private InvoiceService quotationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

       InvoiceBase quotation = quotationService.generateNew(delegateExecution);

        pdfGenaratorUtil.createPdf("quotation", quotation, delegateExecution, "Quotation");
        //delegateExecution.setVariable("quotation", quotation);

    }
}
