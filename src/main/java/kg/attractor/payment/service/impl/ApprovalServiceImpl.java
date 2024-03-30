package kg.attractor.payment.service.impl;

import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.model.Transaction;
import kg.attractor.payment.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kg.attractor.payment.dao.ApprovalDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
    private final ApprovalDao approvalDao;

    @Override
    public List<TransactionDto> getTransactionsRequiringApproval() {
        List<Transaction> transactions = approvalDao.findTransactionsRequiringApproval();
        return transactions.stream().map(transaction -> new TransactionDto(
                transaction.getId(),
                transaction.getSenderAccountId(),
                transaction.getReceiverAccountId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getStatus(),
                transaction.getTransactionTime()
        )).collect(Collectors.toList());
    }
}
