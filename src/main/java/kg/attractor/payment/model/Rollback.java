package kg.attractor.payment.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Rollback {
    private int id;
    private int transactionId;
    private Timestamp rollbackTime;
    private String reason;
}
