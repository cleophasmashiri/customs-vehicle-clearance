package com.muhacha.customs.rest;

import com.muhacha.customs.model.EmployeeContract;
import com.muhacha.customs.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by cleophas on 2018/09/01.
 */

@RestController
@RequestMapping("/api/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    private final static Logger LOG = LoggerFactory.getLogger(ContractController.class);

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/")
    public ResponseEntity<Void> createContract(@RequestBody @Valid EmployeeContract contract) {
        LOG.info("Create a new contract");
        contractService.createContract(contract);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<EmployeeContract> getContract(@PathVariable long contractId) {
        LOG.info("Get a contract");
        return new ResponseEntity<>(contractService.getContract(contractId), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeContract>> getContracts() {
        LOG.info("Get contracts");
        return new ResponseEntity<>(contractService.getContracts(), HttpStatus.OK);
    }

    @DeleteMapping("/{contractId}")
    public ResponseEntity<Void> cancelContract(@PathVariable long contractId) {
        LOG.info("Cancel a contract");
        contractService.cancelContract("");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
