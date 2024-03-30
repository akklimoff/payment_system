package kg.attractor.payment.service;

import kg.attractor.payment.dto.AccountBalanceUpdateDto;
import kg.attractor.payment.dto.AccountDto;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    void createAccount(AccountDto accountDto, Authentication auth);

    BigDecimal getAccountBalance(Integer accountId);
    void updateAccountBalance(AccountBalanceUpdateDto balanceUpdateDto, String userPhone);
    List<AccountDto> findAllAccounts(String userPhone);

}

