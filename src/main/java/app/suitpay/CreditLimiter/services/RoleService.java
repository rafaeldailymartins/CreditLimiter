package app.suitpay.CreditLimiter.services;

import app.suitpay.CreditLimiter.models.Role;
import app.suitpay.CreditLimiter.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public List<Role> getAll() { return repository.findAll(); }

    public Role create(Role role) { return repository.save(role); }

    public Optional<Role> getByName(String name) { return repository.findByName(name); }

    public Optional<Role> getById(Long id) { return repository.findById(id); }

    public void deleteById(Long id) { repository.deleteById(id); }
}
