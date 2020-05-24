package com.muhacha.customs.rest;

import com.muhacha.customs.model.ContractTask;
import com.muhacha.customs.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by cleophas on 2018/09/01.
 */

@RestController
@RequestMapping("/api/task")
public class ContractTaskController {

    private ContractService contractService;

    private final static Logger LOG = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    public ContractTaskController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/accountManagerApprove/{taskId}")
    public void accountManagerApprove(@PathVariable String taskId){
        LOG.info("Account manager approval");
        contractService.accountManagerApprove(taskId);
    }

    @PostMapping("/seniorManagerApprove/{taskId}")
    public void seniorManagerApprove(@PathVariable String taskId){
        LOG.info("Senior manager approval");
        contractService.seniorManagerApprove(taskId);
    }

    @PostMapping("/candidateApprove/{taskId}")
    public void candidateApproval(@PathVariable String taskId){
        LOG.info("Candidate manager approval");
        contractService.candidateApproval(taskId);
    }

    @GetMapping("/{processInstanceId}")
    public ResponseEntity<List<ContractTask>> getTasks(@PathVariable String processInstanceId) {
        LOG.info("Get tasks");
        return new ResponseEntity<>(contractService.getTasks(processInstanceId), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ContractTask>> getAllTasks() {
        LOG.info("Get all tasks");
        return new ResponseEntity<>(contractService.getAllTasks(), HttpStatus.OK);
    }

}
