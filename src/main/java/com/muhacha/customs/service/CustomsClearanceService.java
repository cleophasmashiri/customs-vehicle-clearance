package com.muhacha.customs.service;

import com.muhacha.customs.model.Customer;
import com.muhacha.customs.model.Document;
import com.muhacha.customs.model.VehicleClearance;

import java.util.List;
import java.util.Map;

public interface CustomsClearanceService {

    void createClearanceApplication(Customer customer, Map<String, Document> documents);

    List<VehicleClearance> getClearancesByBassportNumber(String passportNumber);

}
