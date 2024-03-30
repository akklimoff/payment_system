package kg.attractor.payment.service.impl;

import kg.attractor.payment.dao.AccountDao;
import kg.attractor.payment.dao.RollbackDao;
import kg.attractor.payment.dao.TransactionDao;
import kg.attractor.payment.dto.RollbackRequestDto;
import kg.attractor.payment.model.Account;
import kg.attractor.payment.model.Transaction;
import kg.attractor.payment.service.RollbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RollbackServiceImpl implements RollbackService {
    private final RollbackDao rollbackDao;
    private final TransactionDao transactionDao;
    private final AccountDao accountDao;

    @Transactional
    @Override
    public void rollbackTransaction(RollbackRequestDto rollbackRequest) {
        Transaction transaction = transactionDao.findById(rollbackRequest.getTransactionId())
                .orElseThrow(() -> new IllegalStateException("Transaction not found"));

        Account senderAccount = accountDao.findById(transaction.getSenderAccountId())
                .orElseThrow(() -> new IllegalStateException("Sender account not found"));
        Account receiverAccount = accountDao.findById(transaction.getReceiverAccountId())
                .orElseThrow(() -> new IllegalStateException("Receiver account not found"));

        if (receiverAccount.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new IllegalStateException("Insufficient funds on the receiver's account for rollback");
        }

        accountDao.updateBalance(senderAccount.getId(), senderAccount.getBalance().add(transaction.getAmount()));
        accountDao.updateBalance(receiverAccount.getId(), receiverAccount.getBalance().subtract(transaction.getAmount()));

        rollbackDao.createRollbackRecord(rollbackRequest.getTransactionId(), rollbackRequest.getReason());

        transactionDao.updateTransactionStatus(transaction.getId(), "rolled back");

        rollbackDao.deleteApprovalByTransactionId(transaction.getId());

        log.info("Transaction {} has been rolled back successfully for reason: {}", rollbackRequest.getTransactionId(), rollbackRequest.getReason());
    }
}
