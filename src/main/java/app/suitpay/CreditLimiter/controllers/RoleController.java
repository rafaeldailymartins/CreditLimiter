package app.suitpay.CreditLimiter.controllers;

import app.suitpay.CreditLimiter.DTOs.CreateRoleDTO;
import app.suitpay.CreditLimiter.models.Role;
import app.suitpay.CreditLimiter.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService service;
    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<Role> getRoles() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found."));
    }

    @PostMapping
    public Role createRole(@RequestBody CreateRoleDTO body) {
        var role = service.getByName(body.getName());
        if(role.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A role with this name already exists.");
        }
        Role newRole = mapper.map(body, Role.class);
        return  service.create(newRole);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found."));

        service.deleteById(id);
    }
}
