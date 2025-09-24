package app.suitpay.CreditLimiter.services;

import app.suitpay.CreditLimiter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import app.suitpay.CreditLimiter.models.User;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Value("${app.min-vip-limit}")
    private Integer minVipLimit;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAll() { return repository.findAll(); }

    public Optional<User> getById(Long id) { return repository.findById(id); }

    public Optional<User> getByUsername(String username) { return repository.findByUsername(username); }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(user.isVip() && user.getCreditLimit().compareTo(BigDecimal.valueOf(minVipLimit)) < 0) {
            user.setCreditLimit(BigDecimal.valueOf(minVipLimit));
        }
        return repository.save(user);
    }

    @Transactional
    public User update(User user) {
        if(user.isVip() && user.getCreditLimit().compareTo(BigDecimal.valueOf(minVipLimit)) < 0) {
            user.setCreditLimit(BigDecimal.valueOf(minVipLimit));
        }
        return repository.save(user);
    }

    public void deleteById(Long id) { repository.deleteById(id); }
}
