package kg.attractor.payment.service;

import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.dto.TransactionRequestDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getTransactionsForAccount(int accountId, String userPhone);
//    void makeTransaction(TransactionRequestDto transactionRequest, String userPhone);
    List<TransactionDto> getAllTransactions();

}
