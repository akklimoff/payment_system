package kg.attractor.payment.service.impl;

import kg.attractor.payment.dao.AccountDao;
import kg.attractor.payment.dao.TransactionDao;
import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.model.Transaction;
import kg.attractor.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountDao accountDao;
    private final TransactionDao transactionDao;

    @Override
    public List<TransactionDto> getTransactionsForAccount(int accountId, String userPhone) {
        if (!accountDao.existsByAccountIdAndUserPhone(accountId, userPhone)) {
            throw new IllegalStateException("Access denied. User can access only their own account transactions.");
        }
        List<Transaction> transactions = transactionDao.findTransactionsByAccountId(accountId);
        return transactions.stream()
                .map(transaction -> new TransactionDto(
                        transaction.getId(),
                        transaction.getSenderAccountId(),
                        transaction.getReceiverAccountId(),
                        transaction.getAmount(),
                        transaction.getCurrency(),
                        transaction.getStatus(),
                        transaction.getTransactionTime()))
                .collect(Collectors.toList());
    }
}
