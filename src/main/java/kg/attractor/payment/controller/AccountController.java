package kg.attractor.payment.controller;

import kg.attractor.payment.dto.AccountBalanceUpdateDto;
import kg.attractor.payment.dto.AccountDto;
import kg.attractor.payment.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountDto accountDto, Authentication auth) {
        accountService.createAccount(accountDto, auth);
        return ResponseEntity.ok("Successfully created");
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@RequestParam("accountId") Integer accountId) {
        BigDecimal balance = accountService.getAccountBalance(accountId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/balance")
    public ResponseEntity<?> updateAccountBalance(@RequestBody AccountBalanceUpdateDto balanceUpdateDto, Authentication auth) {
        String userPhone = auth.getName();
        accountService.updateAccountBalance(balanceUpdateDto, userPhone);
        return ResponseEntity.ok("Successful");
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccounts(Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userPhone = auth.getName();
        List<AccountDto> accounts = accountService.findAllAccounts(userPhone);
        return ResponseEntity.ok(accounts);
    }
}
