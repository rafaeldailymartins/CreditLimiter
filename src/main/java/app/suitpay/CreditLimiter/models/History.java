package app.suitpay.CreditLimiter.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "tb_history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "old_value")
    private BigDecimal oldValue;

    @Column(name = "new_value")
    private BigDecimal newValue;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "performed_by")
    private Long performedBy;

    @Column(name = "timestamp")
    private Instant timestamp;

    @Column(name = "action")
    private String action;

    public History() {
    }
}
