package kg.attractor.payment.controller;

import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.dto.TransactionRequestDto;
import kg.attractor.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/{accountId}/history")
    public ResponseEntity<List<TransactionDto>> getTransactionHistory(@PathVariable int accountId, Authentication auth) {
        String userPhone = auth.getName();
        List<TransactionDto> transactions = transactionService.getTransactionsForAccount(accountId, userPhone);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionRequestDto transactionRequest, Authentication auth) {
        transactionService.performTransaction(transactionRequest, auth);
        return ResponseEntity.ok().build();
    }
}
