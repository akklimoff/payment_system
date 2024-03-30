package kg.attractor.payment.service;

import kg.attractor.payment.dto.RollbackRequestDto;

public interface RollbackService {
    void rollbackTransaction(RollbackRequestDto rollbackRequest);
    boolean isTransactionRolledBack(int transactionId);
    void markTransactionAsDeleted(int transactionId);
}
