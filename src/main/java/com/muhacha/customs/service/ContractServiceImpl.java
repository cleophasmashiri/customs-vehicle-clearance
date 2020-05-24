package com.muhacha.customs.service;

import com.muhacha.customs.model.ContractTask;
import com.muhacha.customs.model.EmployeeContract;
import com.muhacha.customs.repository.EmployeeContractRepository;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cleophas on 2018/09/01.
 */

@Service
public class ContractServiceImpl implements ContractService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractServiceImpl.class);

    @Autowired
    private EmployeeContractRepository contractRepository;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private TaskService taskService;

    @Autowired
    private Environment env;

    @Override
    public void createContract(EmployeeContract contract) {

        LOGGER.info("Create contract started");

        contractRepository.save(contract);

        // save file
        final String ACCESS_TOKEN = env.getProperty("dropbox.applicationKey");


    }

    @Override
    public EmployeeContract getContract(long contractId) {

        LOGGER.info("Get contract started");

        return contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<EmployeeContract> getContracts() {

        LOGGER.info("Get contracts started");

        return contractRepository.findAll();
    }

    @Override
    public List<ContractTask> getTasks(String processInstanceId) {

        LOGGER.info("Create contract started");

        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();

        return tasks.stream().map(t -> toContractTask(t)).collect(Collectors.toList());

    }

    private ContractTask toContractTask(Task task) {
        ContractTask t = new ContractTask();
        t.setName(task.getName());
        t.setDescription(task.getDescription());
        t.setAssignee(task.getAssignee());
        t.setCreateTime(task.getCreateTime());
        t.setDueDate(task.getDueDate());
        t.setFollowUpDate(task.getFollowUpDate());
        t.setId(task.getId());
        t.setOwner(task.getOwner());
        t.setPriority(task.getPriority());
        t.setProcessInstanceId(task.getProcessInstanceId());
        return t;
    }

    @Override
    public void accountManagerApprove(String taskId) {

        LOGGER.info("Account Manager Approve contract started");

        Map<String, Object> variables = new HashMap<>();
        variables.put("accountManagerApproved", Boolean.TRUE);

        taskService.complete(taskId, variables);

    }

    @Override
    public void seniorManagerApprove(String taskId) {

        LOGGER.info("Senior Manager Approve contract started");

        Map<String, Object> variables = new HashMap<>();
        variables.put("seniorManagerApproved", Boolean.TRUE);

        taskService.complete(taskId, variables);

    }

    @Override
    public void candidateApproval(String taskId) {

        LOGGER.info("Candidate Approval contract started");

        Map<String, Object> variables = new HashMap<>();
        variables.put("accountManagerApproved", Boolean.TRUE);

        taskService.complete(taskId, variables);

    }

    @Override
    public void cancelContract(String processInstanceId) {

        LOGGER.info("Cancel contract started");
    }

    @Override
    public List<ContractTask> getAllTasks() {

        LOGGER.info("Create contract started");

        List<Task> tasks = taskService.createTaskQuery().list();

        return tasks.stream().map(t -> toContractTask(t)).collect(Collectors.toList());
    }
}
