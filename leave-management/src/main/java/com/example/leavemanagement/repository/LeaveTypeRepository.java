package com.example.leavemanagement.repository;

import com.example.leavemanagement.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository
        extends JpaRepository<LeaveType, Long> {
}