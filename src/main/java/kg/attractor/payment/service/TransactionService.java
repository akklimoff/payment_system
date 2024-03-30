package kg.attractor.payment.service;

import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.dto.TransactionRequestDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getTransactionsForAccount(int accountId, String userPhone);
    void performTransaction(TransactionRequestDto transactionRequest, Authentication authentication);
    List<TransactionDto> getAllTransactions();

}
