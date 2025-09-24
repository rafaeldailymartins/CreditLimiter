package app.suitpay.CreditLimiter.controllers;

import app.suitpay.CreditLimiter.DTOs.CreateUserDTO;
import app.suitpay.CreditLimiter.DTOs.UpdateUserCreditLimitDTO;
import app.suitpay.CreditLimiter.DTOs.UserResponseDTO;
import app.suitpay.CreditLimiter.models.Role;
import app.suitpay.CreditLimiter.models.User;
import app.suitpay.CreditLimiter.services.RoleService;
import app.suitpay.CreditLimiter.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.modelmapper.TypeToken;

import java.util.List;
import java.lang.reflect.Type;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModelMapper mapper;


    @GetMapping
    public List<User> getUsers() {
        Type listType = new TypeToken<List<UserResponseDTO>>() {}.getType();
        return mapper.map(service.getAll(), listType);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        User user = service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        return mapper.map(user, UserResponseDTO.class);
    }

    @GetMapping("/me")
    public UserResponseDTO getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String username = jwt.getSubject();

        User user = service.getByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return mapper.map(user, UserResponseDTO.class);
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody @Valid CreateUserDTO body) {
        Role role = roleService.getById(body.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found."));

        var user = service.getByUsername(body.getUsername());
        if(user.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A user with this name already exists.");
        }

        User newUser = new User();
        mapper.map(body, newUser);
        newUser.setId(null);
        newUser.setRole(role);
        return mapper.map(service.create(newUser), UserResponseDTO.class);
    }

    @PutMapping("/{id}/credit-limit")
    public UserResponseDTO updateUserCreditLimit(@PathVariable Long id, @RequestBody @Valid UpdateUserCreditLimitDTO body) {
        var user = service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        user.setCreditLimit(body.getCreditLimit());
        return mapper.map(service.update(user), UserResponseDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        service.deleteById(id);
    }
}
