package app.suitpay.CreditLimiter.controllers;

import app.suitpay.CreditLimiter.DTOs.CreateUserDTO;
import app.suitpay.CreditLimiter.DTOs.UpdateUserCreditLimitDTO;
import app.suitpay.CreditLimiter.DTOs.UserResponseDTO;
import app.suitpay.CreditLimiter.models.History;
import app.suitpay.CreditLimiter.models.Role;
import app.suitpay.CreditLimiter.models.User;
import app.suitpay.CreditLimiter.services.HistoryService;
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

import java.time.Instant;
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
    private HistoryService historyService;
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
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String username = jwt.getSubject();

        User user = service.getByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return mapper.map(user, UserResponseDTO.class);
    }

    @PostMapping
    public UserResponseDTO createUser(Authentication authentication, @RequestBody @Valid CreateUserDTO body) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String username = jwt.getSubject();

        User currentUser = service.getByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

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

        var res = mapper.map(service.create(newUser), UserResponseDTO.class);

        History history = new History();
        history.setNewValue(body.getCreditLimit());
        history.setUserId(res.getId());
        history.setPerformedBy(currentUser.getId());
        history.setTimestamp(Instant.now());
        history.setAction("CREATE_USER");
        historyService.create(history);
        return res;
    }

    @PutMapping("/{id}/credit-limit")
    public UserResponseDTO updateUserCreditLimit(Authentication authentication, @PathVariable Long id, @RequestBody @Valid UpdateUserCreditLimitDTO body) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String username = jwt.getSubject();

        User currentUser = service.getByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        var user = service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        History history = new History();
        history.setOldValue(user.getCreditLimit());

        user.setCreditLimit(body.getCreditLimit());

        var res = mapper.map(service.update(user), UserResponseDTO.class);

        history.setNewValue(body.getCreditLimit());
        history.setUserId(res.getId());
        history.setPerformedBy(currentUser.getId());
        history.setTimestamp(Instant.now());
        history.setAction("UPDATE_USER_CREDIT_LIMIT");
        historyService.create(history);

        return res;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        service.deleteById(id);
    }
}
