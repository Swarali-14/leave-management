package com.example.leavemanagement.repository;

import com.example.leavemanagement.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository
        extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployeeId(Long employeeId);

}