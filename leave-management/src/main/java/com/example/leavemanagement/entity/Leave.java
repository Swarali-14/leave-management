package com.example.leavemanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "leave_request")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalDays;
    private String status; // Pending, Approved, Rejected
    private String leaveType;
    private String reason;
    private String leaveDuration;

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(double totalDays) {
        this.totalDays = totalDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeaveType() {
    return leaveType;
    }

    public void setLeaveType(String leaveType) {
    this.leaveType = leaveType;
    }  

    public String getReason() {
    return reason;
    }

    public void setReason(String reason) {
    this.reason = reason;
    }

    public String getLeaveDuration() {
    return leaveDuration;
    }

    public void setLeaveDuration(String leaveDuration) {
    this.leaveDuration = leaveDuration;
    }
}