package kg.attractor.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RollbackDto {
    private int id;
    private int transactionId;
    private Timestamp rollbackTime;
    private String reason;
}
