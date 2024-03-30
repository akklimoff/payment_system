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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Timestamp;
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

//    @Transactional
//    @Override
//    public void makeTransaction(TransactionRequestDto transactionRequest, String userPhone) throws Exception {
//        Account fromAccount = accountDao.findAccountByIdAndUserPhone(transactionRequest.getFromAccountId(), userPhone)
//                .orElseThrow(() -> new Exception("From account not found or does not belong to the user."));
//        Account toAccount = accountDao.findAccountById(transactionRequest.getToAccountId())
//                .orElseThrow(() -> new Exception("To account not found."));
//
//        if (!fromAccount.getCurrency().equals(toAccount.getCurrency())) {
//            throw new Exception("Accounts have different currencies.");
//        }
//
//        if (fromAccount.getBalance().compareTo(transactionRequest.getAmount()) < 0) {
//            throw new Exception("Insufficient funds.");
//        }
//
//        // Обновляем балансы счетов
//        accountDao.updateBalance(fromAccount.getId(), fromAccount.getBalance().subtract(transactionRequest.getAmount()));
//        accountDao.updateBalance(toAccount.getId(), toAccount.getBalance().add(transactionRequest.getAmount()));
//
//        // Создаем запись транзакции
//        Transaction transaction = Transaction.builder()
//                .senderAccountId(fromAccount.getId())
//                .receiverAccountId(toAccount.getId())
//                .amount(transactionRequest.getAmount())
//                .currency(fromAccount.getCurrency())
//                .status("Completed")
//                .transactionTime(new Timestamp(System.currentTimeMillis()))
//                .build();
//        transactionDao.createTransaction(transaction);
//    }

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
