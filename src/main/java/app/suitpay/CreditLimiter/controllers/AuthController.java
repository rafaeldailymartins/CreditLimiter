package app.suitpay.CreditLimiter.controllers;

import app.suitpay.CreditLimiter.DTOs.AuthRequestDTO;
import app.suitpay.CreditLimiter.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public String authenticate(@RequestBody AuthRequestDTO body) {
        return authService.authenticate(body.getUsername(), body.getPassword());
    }
}
