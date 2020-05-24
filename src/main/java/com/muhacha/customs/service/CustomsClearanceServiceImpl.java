package com.muhacha.customs.service;

import com.muhacha.customs.model.Customer;
import com.muhacha.customs.model.Document;
import com.muhacha.customs.model.VehicleClearance;
import com.muhacha.customs.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class CustomsClearanceServiceImpl implements CustomsClearanceService {


    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void createClearanceApplication(Customer customer, Map<String, Document> documents) {

        customerRepository.save(customer);

    }

    @Override
    public List<VehicleClearance> getClearancesByBassportNumber(String passportNumber) {
        return null;
    }
}
