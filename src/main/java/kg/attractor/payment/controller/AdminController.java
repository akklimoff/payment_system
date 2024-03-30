package kg.attractor.payment.controller;

import kg.attractor.payment.dto.ApprovalRequestDto;
import kg.attractor.payment.dto.RollbackRequestDto;
import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.service.ApprovalService;
import kg.attractor.payment.service.RollbackService;
import kg.attractor.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final TransactionService transactionService;
    private final ApprovalService approvalService;
    private final RollbackService rollbackService;


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

    @PostMapping("/transactions/approval")
    public ResponseEntity<?> approveTransaction(@RequestBody ApprovalRequestDto approvalRequest) {
        approvalService.approveTransaction(approvalRequest.getTransactionId());
        return ResponseEntity.ok("Approved");
    }

    @PostMapping("/rollback")
    public ResponseEntity<?> rollbackTransaction(@RequestBody RollbackRequestDto rollbackRequest) {
        rollbackService.rollbackTransaction(rollbackRequest);
        return ResponseEntity.ok().body("Transaction has been rolled back successfully");
    }


}
