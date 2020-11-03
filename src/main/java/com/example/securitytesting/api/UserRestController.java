package com.example.securitytesting.api;

import com.example.securitytesting.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(OK)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User registerUser(@Valid @RequestBody User user) {
      return userService.registerUser(user.getGender(),
              user.getUsername(), user.getPassword(), user.getFirstName(),
              user.getLastName(), user.getAboutMe(), user.getAge(), user.getEmail());
    }

    @GetMapping
    public List<User> getAllUsers() {
      return userService.findAll().stream().map(User::new).collect(Collectors.toList());
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> findUser(@PathVariable("userName") String userName) {
        return userService.findInsecureByUserName(userName).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
