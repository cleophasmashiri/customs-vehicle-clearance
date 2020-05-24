package com.muhacha.customs.delegates;

import com.muhacha.customs.model.*;
import com.muhacha.customs.repository.CustomerRepository;
import com.muhacha.customs.repository.DocumentRepository;
import com.muhacha.customs.repository.VehicleClearanceRepository;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by cleophas on 2018/08/02.
 */

@Component
public class CreateApplication implements JavaDelegate {


    @Autowired
    private VehicleClearanceRepository vehicleClearanceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DocumentRepository documentRepository;

    private String getValueFromDelegate(String name, DelegateExecution delegateExecution) {
        if (delegateExecution.getVariable(name) != null) {
            return delegateExecution.getVariable(name).toString();
        }
        return "";
    }

    private int getIntValueFromDelegate(String name, DelegateExecution delegateExecution) {
        if (delegateExecution.getVariable(name) != null) {
            String stringVal = delegateExecution.getVariable(name).toString();
            return Integer.parseInt(stringVal);
        }
        return 0;
    }

    private double getDoubleValueFromDelegate(String name, DelegateExecution delegateExecution) {
        if (delegateExecution.getVariable(name) != null) {
            String stringVal = delegateExecution.getVariable(name).toString();
            return Double.parseDouble(stringVal);
        }
        return 0.0;
    }

    private Date getDateValueFromDelegate(String name, DelegateExecution delegateExecution) {
        if (delegateExecution.getVariable(name) != null) {
            String stringVal = delegateExecution.getVariable(name).toString();
            return new Date(stringVal);
        }
        return new Date();
    }

    private VehicleType getVehicleTypeValueFromDelegate(String name, DelegateExecution delegateExecution) {
        if (delegateExecution.getVariable(name) != null) {
            String stringVal = delegateExecution.getVariable(name).toString();
            return VehicleType.valueOf(stringVal);
        }
        return null;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Customer customer = null; //customerRepository.findByPassportNumber("8485848");
        if (customer == null) {
            customer = new Customer();
            customer.setPassportNumber(getValueFromDelegate("passportNumber", delegateExecution));
            customer.setFirstname(getValueFromDelegate("firstName", delegateExecution));
            customer.setLastname(getValueFromDelegate("lastName", delegateExecution));
            customer.setEmail(getValueFromDelegate("emailAddress", delegateExecution));
            customer.setPhoneNumber(getValueFromDelegate("phone", delegateExecution));
            customer.setAddressline1(getValueFromDelegate("addressline1", delegateExecution));
            customer.setAddressCity(getValueFromDelegate("addressCity", delegateExecution));
            customer.setAddressState(getValueFromDelegate("addressState", delegateExecution));
            customer.setAddressPostalCode(getValueFromDelegate("addressPostalCode", delegateExecution));

            customerRepository.save(customer);
        }

        VehicleClearance clearance = new VehicleClearance();
        clearance.setCustomer(customer);
        clearance.setNotes(getValueFromDelegate("notes", delegateExecution));
        clearance.setDateCreated(new Date());
        clearance.setMake(getValueFromDelegate("vehicleMake", delegateExecution));
        clearance.setModel(getValueFromDelegate("model", delegateExecution));
        clearance.setYearOfManufacture(getIntValueFromDelegate("yearOfManufacture", delegateExecution));
        clearance.setMileage(getIntValueFromDelegate("mileage", delegateExecution));
        clearance.setVin(getValueFromDelegate("vin", delegateExecution));
        clearance.setEngineNumber(getValueFromDelegate("engineNumber", delegateExecution));
        clearance.setEngineCapacity(getValueFromDelegate("engineCapacity", delegateExecution));
        //clearance.setVehicleType(getVehicleTypeValueFromDelegate("vehicleType", delegateExecution));
        clearance.setPurchasePrice(getDoubleValueFromDelegate("purchasePrice", delegateExecution));
//        clearance.setPurchaseDate(getDateValueFromDelegate("purchaseDate", delegateExecution));
//        clearance.setDateOfChangeOfOwnership(getDateValueFromDelegate("dateOfChangeOfOwnership", delegateExecution));
        vehicleClearanceRepository.save(clearance);

        addDocument(delegateExecution, DocumentType.PASSPORT, clearance);

        delegateExecution.setVariable("vehicleClearanceId", clearance.getId());


    }

    private void addDocument(DelegateExecution delegateExecution, DocumentType documentType, VehicleClearance clearance) {

        Map<String, String> parameter = new HashMap<>();

        parameter.put(SessionParameter.USER, "test");
        parameter.put(SessionParameter.PASSWORD, "");

        parameter.put(SessionParameter.ATOMPUB_URL, "http://localhost:8081/inmemory/atom");

        parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

        // parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "");

        SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
        Session session = sessionFactory.getRepositories(parameter).get(0).createSession();

        Folder folder = (Folder) session.getObjectByPath("/Customs_Invoices");

        Map<String, Object> properties = new HashMap<>();
        String fileName = UUID.randomUUID().toString();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties.put(PropertyIds.NAME, fileName);

        if (delegateExecution.getVariable("sapcoDocument") != null) {
            System.out.println("delegateExecution.getVariable(\"sapco\")");
            System.out.println(delegateExecution.getVariable("sapcoDocument").toString().length());
            ByteArrayInputStream stream = (ByteArrayInputStream) delegateExecution.getVariable("sapcoDocument");
            int fileLength = 0;
            try {
                byte[] arr = IOUtils.toByteArray(stream);
                fileLength = arr.length;
            } catch (IOException e) {
                e.printStackTrace();
            }
            ContentStream contentStream = new ContentStreamImpl(fileName, BigInteger.valueOf(fileLength), "application/pdf", stream);
            folder.createDocument(properties, contentStream, VersioningState.NONE);
            Document document = new Document();
            document.setName(fileName);
            document.setDocumentType(documentType);
            document.setVehicleClearance(clearance);
            documentRepository.save(document);
        }

    }
}
