package kg.attractor.payment.controller;

import kg.attractor.payment.dto.AccountDto;
import kg.attractor.payment.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

//    @PostMapping
//    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto, Principal principal) {
//        String userPhone = principal.getName();
//        AccountDto createdAccount = accountService.createAccount(userPhone, accountDto);
//        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody AccountDto accountDto, Authentication auth) {
        accountService.createAccount(accountDto, auth);
        return ResponseEntity.ok().build();
    }
}
