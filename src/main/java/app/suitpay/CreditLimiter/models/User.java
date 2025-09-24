package app.suitpay.CreditLimiter.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "is_vip", nullable = false)
    private boolean isVip;

    @Column(name = "credit_limit", nullable = false, precision = 19, scale = 4)
    @DecimalMin(value = "0.0", inclusive = true, message = "Credit limit must be positive")
    private BigDecimal creditLimit;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
    }

    public User(String username, String password, String fullName, boolean isVip, BigDecimal creditLimit, Role role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.isVip = isVip;
        this.creditLimit = creditLimit;
        this.role = role;
    }

    public User(Long id, String username, String password, String fullName, boolean isVip, BigDecimal creditLimit, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.isVip = isVip;
        this.creditLimit = creditLimit;
        this.role = role;
    }
}
