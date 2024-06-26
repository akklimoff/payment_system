package kg.attractor.payment.dao;

import kg.attractor.payment.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Transaction> findTransactionsByAccountId(int accountId) {
        String sql = "SELECT * FROM transactions WHERE sender_account_id = ? OR receiver_account_id = ?";
        return jdbcTemplate.query(sql,
                new Object[]{accountId, accountId},
                (rs, rowNum) -> Transaction.builder()
                        .id(rs.getInt("id"))
                        .senderAccountId(rs.getInt("sender_account_id"))
                        .receiverAccountId(rs.getInt("receiver_account_id"))
                        .amount(rs.getBigDecimal("amount"))
                        .currency(rs.getString("currency"))
                        .status(rs.getString("status"))
                        .transactionTime(rs.getTimestamp("transaction_time"))
                        .build());
    }

    public List<Transaction> findAllTransactions() {
        String sql = "SELECT * FROM transactions";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Transaction.builder()
                        .id(rs.getInt("id"))
                        .senderAccountId(rs.getInt("sender_account_id"))
                        .receiverAccountId(rs.getInt("receiver_account_id"))
                        .amount(rs.getBigDecimal("amount"))
                        .currency(rs.getString("currency"))
                        .status(rs.getString("status"))
                        .transactionTime(rs.getTimestamp("transaction_time"))
                        .build());
    }

    public List<Transaction> findTransactionsRequiringApproval() {
        String sql = "SELECT * FROM transactions WHERE status = 'pending approval'";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Transaction.builder()
                        .id(rs.getInt("id"))
                        .senderAccountId(rs.getInt("sender_account_id"))
                        .receiverAccountId(rs.getInt("receiver_account_id"))
                        .amount(rs.getBigDecimal("amount"))
                        .currency(rs.getString("currency"))
                        .status(rs.getString("status"))
                        .transactionTime(rs.getTimestamp("transaction_time"))
                        .build());
    }

    public Optional<Transaction> findById(int transactionId) {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try {
            Transaction transaction = jdbcTemplate.queryForObject(sql, new Object[]{transactionId},
                    (rs, rowNum) -> Transaction.builder()
                            .id(rs.getInt("id"))
                            .senderAccountId(rs.getInt("sender_account_id"))
                            .receiverAccountId(rs.getInt("receiver_account_id"))
                            .amount(rs.getBigDecimal("amount"))
                            .currency(rs.getString("currency"))
                            .status(rs.getString("status"))
                            .transactionTime(rs.getTimestamp("transaction_time"))
                            .build());
            return Optional.ofNullable(transaction);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateTransactionStatus(int transactionId, String status) {
        String sql = "UPDATE transactions SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, transactionId);
    }

    public void save(Transaction transaction) {
        String sql = "INSERT INTO transactions (sender_account_id, receiver_account_id, amount, currency, status, transaction_time) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                transaction.getSenderAccountId(),
                transaction.getReceiverAccountId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getStatus(),
                transaction.getTransactionTime());
    }
}
