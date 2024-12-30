package com.leo.banking.loan;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    @PostMapping("/application")
    public String loanApplication() {
        System.out.println("Application started");
        return "Hello World";
    }
    @PostMapping("/approval")
    public String loanApproval() {
        return "Hello World";
    }

}
