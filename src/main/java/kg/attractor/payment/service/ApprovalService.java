package kg.attractor.payment.service;

import kg.attractor.payment.dto.TransactionDto;

import java.util.List;

public interface ApprovalService {
    List<TransactionDto> getTransactionsRequiringApproval();
}
