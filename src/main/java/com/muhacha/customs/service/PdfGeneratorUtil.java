package com.muhacha.customs.service;

import com.muhacha.customs.model.InvoiceBase;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import static org.camunda.bpm.engine.variable.Variables.fileValue;

@Component
public class PdfGeneratorUtil {

    @Autowired
    private TemplateEngine templateEngine;
    public void createPdf(String templateName, InvoiceBase quotation, DelegateExecution delegateExecution, String documentName) throws Exception {
        Assert.notNull(templateName, "The templateName can not be null");

        Map map = new HashMap();
         map.put("documentType", templateName);
         map.put("customerName", quotation.getCustomerName());
         map.put("addressline1", quotation.getAddressline1());
         map.put("addressCity", quotation.getAddressCity());
         map.put("addressState", quotation.getAddressState());
         map.put("addressPostalCode", quotation.getAddressPostalCode());
         map.put("totalAmount", quotation.getTotalAmount());
         map.put("lineItems", quotation.getLineItems());
        Context ctx = new Context();
        if (map != null) {
            Iterator itMap = map.entrySet().iterator();
            while (itMap.hasNext()) {
                Map.Entry pair = (Map.Entry) itMap.next();
                ctx.setVariable(pair.getKey().toString(), pair.getValue());
            }
        }

        String processedHtml = templateEngine.process(templateName, ctx);
        FileOutputStream os = null;
        String fileName = UUID.randomUUID().toString();
        try {
            final File outputFile = File.createTempFile(fileName, ".pdf");
            os = new FileOutputStream(outputFile);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml);
            renderer.layout();
            renderer.createPDF(os, false);
            renderer.finishPDF();
            System.out.println("PDF created successfully");

            InputStream inputStream  = new FileInputStream(outputFile);
            delegateExecution.setVariable(documentName, fileValue(documentName)
                    .file(inputStream)
                    .mimeType("application/pdf")
                    .create());

        }
        finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) { /*ignore*/ }
            }
        }
    }
}
