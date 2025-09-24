package app.suitpay.CreditLimiter;

import app.suitpay.CreditLimiter.models.Role;
import app.suitpay.CreditLimiter.models.User;
import app.suitpay.CreditLimiter.repositories.RoleRepository;
import app.suitpay.CreditLimiter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class CreditLimiterApplication implements CommandLineRunner {
    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.role}")
    private String adminRole;

    public static void main(String[] args) {
		SpringApplication.run(CreditLimiterApplication.class, args);
	}

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByUsername(adminName).isEmpty()){
            User user = new User();
            user.setUsername(adminName);
            user.setPassword(passwordEncoder.encode(adminPassword));
            user.setFullName(adminName);
            user.setVip(true);
            user.setCreditLimit(BigDecimal.valueOf(0));
            user.setRole(new Role(adminRole));
            userRepository.save(user);
        };


    }
}
