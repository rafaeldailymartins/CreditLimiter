package app.suitpay.CreditLimiter.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateRoleDTO {
    @NotBlank(message = "Name is required")
    private String name;
}
