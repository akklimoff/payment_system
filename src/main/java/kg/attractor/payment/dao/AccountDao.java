package kg.attractor.payment.dao;

import kg.attractor.payment.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountDao {
    private final JdbcTemplate jdbcTemplate;

    public void createAccount(Account account) {
        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }
        String sql = "INSERT INTO accounts (user_phone, currency, balance) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, account.getUserPhone(), account.getCurrency(), account.getBalance());
    }

    public int countAccountsByUserPhone(String userPhone) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE user_phone = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userPhone);
    }

    public BigDecimal getBalanceByAccountId(Integer accountId) {
        try {
            String sql = "SELECT balance FROM accounts WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{accountId}, BigDecimal.class);
        } catch (EmptyResultDataAccessException e) {
            return BigDecimal.ZERO;
        }
    }

    public boolean isAccountBelongsToUser(Integer accountId, String userPhone) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE id = ? AND user_phone = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{accountId, userPhone}, Integer.class);
        return count != null && count > 0;
    }

    public void updateBalance(Integer accountId, BigDecimal amount) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        jdbcTemplate.update(sql, amount, accountId);
    }

    public List<Account> findAllAccountsByUserPhone(String userPhone) {
        String sql = "SELECT * FROM accounts WHERE user_phone = ?";
        return jdbcTemplate.query(sql, new Object[]{userPhone},
                (rs, rowNum) -> Account.builder()
                        .id(rs.getInt("id"))
                        .userPhone(rs.getString("user_phone"))
                        .currency(rs.getString("currency"))
                        .balance(rs.getBigDecimal("balance"))
                        .build()
        );
    }

    public boolean existsByAccountIdAndUserPhone(int accountId, String userPhone) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE id = ? AND user_phone = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{accountId, userPhone}, Integer.class);
        return count != null && count > 0;
    }

    public Optional<Account> findById(int accountId) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try {
            Account account = jdbcTemplate.queryForObject(sql, new Object[]{accountId},
                    (rs, rowNum) -> Account.builder()
                            .id(rs.getInt("id"))
                            .userPhone(rs.getString("user_phone"))
                            .currency(rs.getString("currency"))
                            .balance(rs.getBigDecimal("balance"))
                            .build());
            return Optional.ofNullable(account);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
