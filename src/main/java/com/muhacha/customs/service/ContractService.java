package com.muhacha.customs.service;

import com.muhacha.customs.model.ContractTask;
import com.muhacha.customs.model.EmployeeContract;

import java.util.List;

/**
 * Created by cleophas on 2018/09/01.
 */

public interface ContractService {

    EmployeeContract getContract(long contractId);

    void createContract(EmployeeContract contract);

    List<ContractTask> getTasks(String processInstanceId);

    List<EmployeeContract> getContracts();

    void accountManagerApprove(String taskId);

    void seniorManagerApprove(String taskId);

    void candidateApproval(String taskId);

    void cancelContract(String processInstanceId);

    List<ContractTask> getAllTasks();
}
