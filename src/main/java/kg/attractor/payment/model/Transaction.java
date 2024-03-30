package kg.attractor.payment.model;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Transaction {
    private int id;
    private int senderAccountId;
    private int receiverAccountId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private Timestamp transactionTime;
}
