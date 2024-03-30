package kg.attractor.payment.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Approvals {
    private int id;
    private int transactionId;
    private boolean isApproved;
    private Timestamp approvalTime;
}
