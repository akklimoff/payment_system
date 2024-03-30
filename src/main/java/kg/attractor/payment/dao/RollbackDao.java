package kg.attractor.payment.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Component
@RequiredArgsConstructor
public class RollbackDao {
    private final JdbcTemplate jdbcTemplate;

    public void createRollbackRecord(int transactionId, String reason) {
        String sql = "INSERT INTO rollbacks (transaction_id, rollback_time, reason) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, transactionId, new Timestamp(System.currentTimeMillis()), reason);
    }

    public void deleteApprovalByTransactionId(int transactionId) {
        String sql = "DELETE FROM approvals WHERE transaction_id = ?";
        jdbcTemplate.update(sql, transactionId);
    }

    public boolean existsByTransactionId(int transactionId) {
        String sql = "SELECT COUNT(*) FROM rollbacks WHERE transaction_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[] {transactionId}, Integer.class);
        return count != null && count > 0;
    }

}
