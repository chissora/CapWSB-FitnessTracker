package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final BasicUserMapper basicUserMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAllUsers().stream()
                .map(userMapper::toDto)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/simple")
    public ResponseEntity<List<BasicUserDto>> getAllBasicUsers() {
        List<BasicUserDto> basicUsers = userService.findAllBasicUsers().stream()
                .map(basicUserMapper::toDto)
                .toList();
        return ResponseEntity.ok(basicUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/email")
    public ResponseEntity<List<UserEmailDto>> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email)
                .stream()
                .map(user -> new UserEmailDto(user.getId(), user.getEmail()))
                .toList());
    }

    @GetMapping("/older/{date}")
    public ResponseEntity<List<UserDto>> getUsersOlderThan(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<UserDto> users = userService.findUsersOlderThan(date).stream()
                .map(userMapper::toDto)
                .toList();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(201).body(createdUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<UserEmailDto>> searchUserByEmailFragment(@RequestParam String emailFragment) {
        List<UserEmailDto> users = userService.findUserByEmailFragment(emailFragment).stream()
                .map(user -> new UserEmailDto(user.getId(), user.getEmail()))
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/olderThan")
    public ResponseEntity<List<UserDto>> searchUserOlderThan(@RequestParam int age) {
        LocalDate date = LocalDate.now().minusYears(age);
        List<UserDto> users = userService.findUsersOlderThan(date).stream()
                .map(userMapper::toDto)
                .toList();
        return ResponseEntity.ok(users);
    }
}
