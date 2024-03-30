package kg.attractor.payment.controller;

import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.service.ApprovalService;
import kg.attractor.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final TransactionService transactionService;
    private final ApprovalService approvalService;

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<TransactionDto> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transactions/approval")
    public ResponseEntity<List<TransactionDto>> getTransactionsRequiringApproval() {
        List<TransactionDto> transactions = approvalService.getTransactionsRequiringApproval();
        return ResponseEntity.ok(transactions);
    }

}
