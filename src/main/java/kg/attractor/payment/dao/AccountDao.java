package kg.attractor.payment.dao;

import kg.attractor.payment.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.PreparedStatement;

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
}
