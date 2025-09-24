package app.suitpay.CreditLimiter.DTOs;

import app.suitpay.CreditLimiter.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UserResponseDTO {
    private Long id;
    private String username;
    private String fullName;
    private boolean isVip;
    private BigDecimal creditLimit;
    private Role role;
}
