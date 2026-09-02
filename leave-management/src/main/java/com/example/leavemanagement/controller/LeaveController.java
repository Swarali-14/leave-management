package com.example.leavemanagement.controller;
import com.example.leavemanagement.entity.Leave;
import com.example.leavemanagement.service.LeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@CrossOrigin(originPatterns = "http://localhost:*")

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    // API 1: Apply for leave
    @PostMapping
    public ResponseEntity<Leave> applyLeave(
            @RequestBody Leave leave) {

        Leave savedLeave = leaveService.applyLeave(leave);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedLeave);
    }

    // API 2: View all leave requests
    @GetMapping
    public ResponseEntity<List<Leave>> getAllLeaves() {

        List<Leave> leaves = leaveService.getAllLeaves();

        return ResponseEntity.ok(leaves);
    }

    // API 3: View leave requests for one employee
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Leave>> getLeavesByEmployeeId(
            @PathVariable Long employeeId) {

        List<Leave> leaves =
                leaveService.getLeavesByEmployeeId(employeeId);

        return ResponseEntity.ok(leaves);
    }

    @PutMapping("/cancel/{id}")
        public Leave cancelLeave(
        @PathVariable Long id) {

    return leaveService.cancelLeave(id);
}
}