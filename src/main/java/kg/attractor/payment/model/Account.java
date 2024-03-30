package kg.attractor.payment.model;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {
    private int id;
    private String userPhone;
    private String currency;
    private BigDecimal balance;
}
