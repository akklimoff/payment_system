package kg.attractor.payment.service.impl;

import kg.attractor.payment.dao.AccountDao;
import kg.attractor.payment.dto.AccountBalanceUpdateDto;
import kg.attractor.payment.dto.AccountDto;
import kg.attractor.payment.model.Account;
import kg.attractor.payment.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;

    @Override
    public void createAccount(AccountDto accountDto, Authentication auth) {
        String userPhone = auth.getName();

        int accountCount = accountDao.countAccountsByUserPhone(userPhone);
        if (accountCount >= 3) {
            throw new IllegalStateException("User can have only up to 3 accounts");
        }

        Account account = Account.builder()
                .userPhone(userPhone)
                .currency(accountDto.getCurrency())
                .balance(accountDto.getBalance())
                .build();
        accountDao.createAccount(account);
    }

    @Override
    public BigDecimal getAccountBalance(Integer accountId) {
        return accountDao.getBalanceByAccountId(accountId);
    }

    @Override
    public void updateAccountBalance(AccountBalanceUpdateDto balanceUpdateDto, String userPhone) {
        if (!accountDao.isAccountBelongsToUser(balanceUpdateDto.getAccountId(), userPhone)) {
            throw new IllegalStateException("Account does not belong to the user");
        }
        accountDao.updateBalance(balanceUpdateDto.getAccountId(), balanceUpdateDto.getAmount());
    }

    @Override
    public List<AccountDto> findAllAccounts(String userPhone) {
        List<Account> accounts = accountDao.findAllAccountsByUserPhone(userPhone);
        return accounts.stream()
                .map(account -> new AccountDto(
                        account.getId(),
                        account.getCurrency(),
                        account.getBalance()
                ))
                .collect(Collectors.toList());
    }
}
