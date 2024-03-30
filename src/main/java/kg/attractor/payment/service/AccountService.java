package kg.attractor.payment.service;

import kg.attractor.payment.dto.AccountDto;
import org.springframework.security.core.Authentication;

public interface AccountService {
    void createAccount(AccountDto accountDto, Authentication auth);
}

