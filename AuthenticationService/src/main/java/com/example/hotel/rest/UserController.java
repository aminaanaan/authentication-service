package com.example.hotel.rest;

import com.example.hotel.dto.*;
import com.example.hotel.exception.*;
import com.example.hotel.models.*;
import com.example.hotel.repository.*;
import com.example.hotel.security.*;
import com.example.hotel.service.*;
import io.swagger.annotations.*;
import lombok.*;
import org.springframework.amqp.rabbit.core.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;


@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    public static final String topicExchangeName = "spring-boot-exchange";

    private UserService userService;

    private UserRepository userRepository;

    private RabbitTemplate rabbitTemplate;

    @ApiOperation(value = "Create User", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping()
    public String create(@Valid  @RequestBody UserRequest korisnik) {
        return userService.create(korisnik);
    }

    @ApiOperation(value = "Get All Users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/all")
    List<User> all() {
        return userService.findAll();
    }

    @ApiOperation(value = "Get User By Id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/{id}")
    User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @ApiOperation(value = "Update User By Id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping()
    String update(@Valid  @RequestBody User user) {
        return userService.updateKorisnik(user);}


    @ApiOperation(value = "Delete User By Id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping("/{id}")
    String deleteKorisnik(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public UserEntity getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }



}
