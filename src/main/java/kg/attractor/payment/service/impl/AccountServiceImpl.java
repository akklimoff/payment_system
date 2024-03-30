package kg.attractor.payment.service.impl;

import kg.attractor.payment.dao.AccountDao;
import kg.attractor.payment.dto.AccountDto;
import kg.attractor.payment.model.Account;
import kg.attractor.payment.model.User;
import kg.attractor.payment.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;

    @Override
    public void createAccount(AccountDto accountDto, Authentication auth) {
        String userPhone = auth.getName();

        Account account = Account.builder()
                .userPhone(userPhone)
                .currency(accountDto.getCurrency())
                .balance(accountDto.getBalance())
                .build();
        accountDao.createAccount(account);
    }
}
