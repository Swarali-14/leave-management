package com.example.leavemanagement.service;

import com.example.leavemanagement.entity.Employee;
import com.example.leavemanagement.entity.Leave;
import com.example.leavemanagement.repository.EmployeeRepository;
import com.example.leavemanagement.repository.LeaveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.List;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveService(
            LeaveRepository leaveRepository,
            EmployeeRepository employeeRepository) {

        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Leave applyLeave(Leave leave) {

        // Validate employee ID
        if (leave.getEmployeeId() == null) {
            throw new RuntimeException("Employee ID is required");
        }

        // Find employee in the database
        Employee employee = employeeRepository
                .findById(leave.getEmployeeId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found with ID: "
                                        + leave.getEmployeeId()
                        )
                );

        // Validate leave dates
        if (leave.getStartDate() == null ||
                leave.getEndDate() == null) {

            throw new RuntimeException(
                    "Start date and end date are required"
            );
        }

        // End date cannot be before start date
        if (leave.getEndDate().isBefore(leave.getStartDate())) {
            throw new RuntimeException(
                    "End date cannot be before start date"
            );
        }


        // Check duplicate leave requests

        List<Leave> existingLeaves =
        leaveRepository.findByEmployeeId(
                leave.getEmployeeId()
        );

    for (Leave existingLeave : existingLeaves) {

    if (!leave.getEndDate().isBefore(existingLeave.getStartDate())
            &&
        !leave.getStartDate().isAfter(existingLeave.getEndDate())) {

        throw new RuntimeException(
                "Leave already applied for selected dates"
        );
    }
}

        // Calculate total days, including both dates
        int totalDays = 0;

        LocalDate currentDate = leave.getStartDate();

        while (!currentDate.isAfter(leave.getEndDate())) {

         DayOfWeek day = currentDate.getDayOfWeek();

            if (day != DayOfWeek.SATURDAY &&
            day != DayOfWeek.SUNDAY)
             {

        totalDays++;
    }
    
    currentDate = currentDate.plusDays(1);

    }

     if (totalDays == 0) {
    throw new RuntimeException(
            "Selected dates contain no working days"
    );
    }
        // Validate employee leave balance
        if (totalDays > employee.getLeaveBalance()) {
            throw new RuntimeException(
                    "Insufficient leave balance. Available balance: "
                            + employee.getLeaveBalance()
            );
        }

        // Set calculated values
        double calculatedDays = totalDays;

        if ("HALF_DAY".equals(
        leave.getLeaveDuration())) {

        calculatedDays = 0.5;
        }

        leave.setTotalDays(calculatedDays);
        leave.setStatus("APPROVED");

        // Deduct days from employee's leave balance
        employee.setLeaveBalance(
                employee.getLeaveBalance() - totalDays
        );

        // Update employee
        employeeRepository.save(employee);

        // Save and return leave request
        return leaveRepository.save(leave);
    }

    // View all leave requests
    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    // View leave requests for one employee
    public List<Leave> getLeavesByEmployeeId(Long employeeId) {

        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException(
                    "Employee not found with ID: " + employeeId
            );
        }

        return leaveRepository.findByEmployeeId(employeeId);
    }

    @Transactional
    public Leave cancelLeave(Long leaveId) {

    Leave leave = leaveRepository
            .findById(leaveId)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Leave not found with ID: "
                                    + leaveId
                    )
            );

    if ("CANCELLED".equals(leave.getStatus())) {

        throw new RuntimeException(
                "Leave is already cancelled"
        );
    }

    Employee employee = employeeRepository
            .findById(leave.getEmployeeId())
            .orElseThrow(() ->
                    new RuntimeException(
                            "Employee not found"
                    )
            );

    // Restore leave balance
    employee.setLeaveBalance(
            employee.getLeaveBalance()
                         + leave.getTotalDays()
    );

    employeeRepository.save(employee);

    // Update leave status
    leave.setStatus("CANCELLED");

    return leaveRepository.save(leave);
}
}