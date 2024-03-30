package kg.attractor.payment.dao;

import kg.attractor.payment.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApprovalDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Transaction> findTransactionsRequiringApproval() {
        String sql = """
                 SELECT t.* FROM transactions t
                 INNER JOIN approvals a ON t.id = a.transaction_id
                 WHERE a.is_approved = false
                 """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> Transaction.builder()
                .id(rs.getInt("id"))
                .senderAccountId(rs.getInt("sender_account_id"))
                .receiverAccountId(rs.getInt("receiver_account_id"))
                .amount(rs.getBigDecimal("amount"))
                .currency(rs.getString("currency"))
                .status(rs.getString("status"))
                .transactionTime(rs.getTimestamp("transaction_time"))
                .build());
    }

    public void approveTransaction(int transactionId) {
        String sqlUpdateApproval = "UPDATE approvals SET is_approved = true WHERE transaction_id = ?";
        jdbcTemplate.update(sqlUpdateApproval, transactionId);

        String sqlUpdateTransaction = "UPDATE transactions SET status = 'Approved' WHERE id = ?";
        jdbcTemplate.update(sqlUpdateTransaction, transactionId);
    }
}
