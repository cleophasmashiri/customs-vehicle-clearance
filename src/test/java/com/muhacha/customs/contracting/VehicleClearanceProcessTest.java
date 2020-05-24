package com.muhacha.customs.contracting;

import com.muhacha.customs.CustomsApiApplication;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.task;
import static org.camunda.bpm.engine.variable.Variables.fileValue;


@RunWith(SpringRunner.class)
@SpringBootTest
@Deployment(resources = "CustomsProcess.bpmn")
public class VehicleClearanceProcessTest {

    private final String PROCESS_NAME = "CustomsProcess";

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void whenMissingRequiredFieldsThenRequestMoreInfoTaskCreated() {

        Map<String, Object> submittedData = new HashMap<>();

        submittedData.put("emailAddress", "BenSmith@gmail.com");
        ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey("CustomsProcess", submittedData);

        assertThat(processInstance)
                .isStarted()
                .hasPassed("initiate_application", "validate_application", "send_request_Info_email")
                .isWaitingAt("provide_more_info")
                .task()
                .hasCandidateGroup("customers");
    }

    private Map<String, Object> getValidCleranceData(String passportNumber) {
        Map<String, Object> submittedData = new HashMap<>();
        submittedData.put("passportNumber", passportNumber);
        submittedData.put("firstName", "Ben");
        submittedData.put("lastName", "Smith");
        submittedData.put("emailAddress", "BenSmith@gmail.com");
        submittedData.put("phone", "077675567");
        submittedData.put("addressline1", "19 High Glen");
        submittedData.put("addressCity", "Harare");
        submittedData.put("addressState", "Mashonaland Central");
        submittedData.put("addressPostalCode", "1291");

        submittedData.put("vehicleMake", "Toyota");
        submittedData.put("model", "Yaris");
        submittedData.put("yearOfManufacture", "2009");
        submittedData.put("mileage", "100000");
        submittedData.put("vin", "Ya8474765XX");
        submittedData.put("engineNumbe, "01/01/2016");
        submittedData.put("dateOfChangeOfOwnership", "01/01/2016");
        String sapco = "sapco.pdf";
        InputStream sapcoDocumentStream = CustomsApiApplication.class.getClassLoader().getResourceAsStream(sapco);
        submittedData.put("sapcoDocument", fileValue(sapco)
                .file(sapcoDocumentStream)
                .mimeType("application/pdf")
                .create());
        return submittedData;
    }r", "BKS9995");
            submittedData.put("engineCapacity", "3000cc");
        submittedData.put("vehicleType", "Sedan");
        submittedData.put("prchasePrice", "200000");
        submittedData.put("purchaseDate"


    // https://forum.camunda.org/t/testing-timer-events-in-processes/3131/2
    // https://docs.camunda.org/manual/7.10/reference/bpmn20/events/timer-events/
    // https://github.com/jkanschik/camunda-timer-test/blob/master/src/test/main/processTest/LoggerDelegateTest.java
    @Test
    public void whenCustomsOfficeTakesTooLongThenReminderEmailShouldBeSent() {
        Map<String, Object> request = getValidCleranceData("RTTTTT9595959");
        ProcessInstance processInstance = processEngine()
                .getRuntimeService()
                .startProcessInstanceByKey(PROCESS_NAME, request);

        assertThat(processInstance)
                .isStarted()
                .hasPassed("initiate_application", "generate_quote", "email_quote_to_customer")
                .isWaitingAt("custom_officer_review_application")
                .task()
                .hasCandidateGroup("CustomOfficers");

        long time = ClockUtil.getCurrentTime().getTime();
        long seconds = 10 * 24 * 60 * 60;
        ClockUtil.setCurrentTime(new Date(time+seconds*1000));

        Job job =  processEngine().getManagementService().createJobQuery().singleResult();
        processEngine().getManagementService().executeJob(job.getId());

        assertThat(processInstance)
                .hasPassed("send_customs_officer_reminder_email");

    }

    @Test
    public void whenSupervisorRejectsThenApplicationShouldEnd() {
        Map<String, Object> request = getValidCleranceData("RTTT9393939");
        ProcessInstance processInstance = processEngine()
                .getRuntimeService()
                .startProcessInstanceByKey(PROCESS_NAME, request);

        assertThat(processInstance)
                .isStarted()
                .hasPassed("initiate_application", "generate_quote", "email_quote_to_customer")
                .isWaitingAt("custom_officer_review_application")
                .task()
                .hasCandidateGroup("CustomOfficers");

        complete(task(), Variables.createVariables().putValue("customsOfficerApproved", Boolean.TRUE));

        assertThat(processInstance)
                .isWaitingAt("approve_duty_invoice")
                .task()
                .hasCandidateGroup("Customers");

        complete(task(), Variables.createVariables().putValue("supervisorApproved", Boolean.FALSE));

        assertThat(processInstance)
                .isEnded();


    }


    @Test
    public void whenValidaCarClearanceDataSuppliedThenCustomerShouldReceiveVehicleDocuments() {

        Map<String, Object> submittedData = getValidCleranceData("AX4584848");

        ProcessInstance processInstance = processEngine()
                .getRuntimeService()
                .startProcessInstanceByKey("CustomsProcess", submittedData);

        assertThat(processInstance)
                .isStarted()
                .hasPassed("initiate_application", "generate_quote", "email_quote_to_customer")
                .isWaitingAt("custom_officer_review_application")
                .task()
                .hasCandidateGroup("CustomOfficers");

        complete(task(), Variables.createVariables().putValue("customsOfficerApproved", Boolean.TRUE));

        assertThat(processInstance)
                .isWaitingAt("approve_duty_invoice")
                .task()
                .hasCandidateGroup("Customers");

        complete(task(), Variables.createVariables().putValue("supervisorApproved", Boolean.TRUE));

        assertThat(processInstance)
                .hasPassed("email_duty_invoice")
                .isWaitingAt("customer_invoice_payment")
                .task()
                .hasCandidateGroup("customers");

        complete(task(), Variables.createVariables().putValue("amount", 10000));

        assertThat(processInstance)
                .hasPassed("process_invoice_payment", "generate_car_documents", "notify_customer_to_collect_docs")
                .isWaitingAt("collect_car_docments")
                .task()
                .hasCandidateGroup("customers");

        complete(task(), Variables.createVariables().putValue("collectedDocs", Boolean.TRUE));

        assertThat(processInstance)
                .isEnded();
    }

}
