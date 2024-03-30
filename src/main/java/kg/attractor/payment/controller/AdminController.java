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
@RequestMapping("api/admin/transactions")
@RequiredArgsConstructor
public class AdminController {
    private final TransactionService transactionService;
    private final ApprovalService approvalService;
    private final RollbackService rollbackService;


    @GetMapping()
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<TransactionDto> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/approval")
    public ResponseEntity<List<TransactionDto>> getTransactionsRequiringApproval() {
        List<TransactionDto> transactions = approvalService.getTransactionsRequiringApproval();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/approval")
    public ResponseEntity<?> approveTransaction(@RequestBody ApprovalRequestDto approvalRequest) {
        approvalService.approveTransaction(approvalRequest.getTransactionId());
        return ResponseEntity.ok("Approved");
    }

    @PostMapping("/rollback")
    public ResponseEntity<?> rollbackTransaction(@RequestBody RollbackRequestDto rollbackRequest) {
        rollbackService.rollbackTransaction(rollbackRequest);
        return ResponseEntity.ok().body("Transaction has been rolled back successfully for reason: " + rollbackRequest.getReason());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable int id) {
        rollbackService.markTransactionAsDeleted(id);
        return ResponseEntity.ok().build();
    }

}
