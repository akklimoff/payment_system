package kg.attractor.payment.service.impl;

import kg.attractor.payment.dao.AccountDao;
import kg.attractor.payment.dao.TransactionDao;
import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.dto.TransactionRequestDto;
import kg.attractor.payment.model.Account;
import kg.attractor.payment.model.Transaction;
import kg.attractor.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
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

    @Transactional
    @Override
    public void performTransaction(TransactionRequestDto transactionRequest, Authentication authentication) {
        String userPhone = authentication.getName();
        Account senderAccount = accountDao.findById(transactionRequest.getSenderAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        if (!senderAccount.getUserPhone().equals(userPhone)) {
            throw new IllegalStateException("The sender account does not belong to the authenticated user");
        }
        Account receiverAccount = accountDao.findById(transactionRequest.getReceiverAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

        if (!senderAccount.getCurrency().equals(receiverAccount.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch between sender and receiver accounts");
        }
        if (senderAccount.getBalance().compareTo(transactionRequest.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds in sender account");
        }
        BigDecimal newSenderBalance = senderAccount.getBalance().subtract(transactionRequest.getAmount());
        accountDao.updateBalance(senderAccount.getId(), newSenderBalance);

        BigDecimal newReceiverBalance = receiverAccount.getBalance().add(transactionRequest.getAmount());
        accountDao.updateBalance(receiverAccount.getId(), newReceiverBalance);
        Transaction transaction = Transaction.builder()
                .senderAccountId(senderAccount.getId())
                .receiverAccountId(receiverAccount.getId())
                .amount(transactionRequest.getAmount())
                .currency(senderAccount.getCurrency())
                .status("completed")
                .transactionTime(new Timestamp(System.currentTimeMillis()))
                .build();

        transactionDao.save(transaction);

        log.info("Transaction from account ID {} to account ID {} for amount {} {} completed successfully.",
                senderAccount.getId(), receiverAccount.getId(), transactionRequest.getAmount(), senderAccount.getCurrency());
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = transactionDao.findAllTransactions();
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
