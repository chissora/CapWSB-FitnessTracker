package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    void deleteUser(Long userId);

    User updateUser(Long userId, User user);

    Optional<User> getUser(Long userId);

    Optional<User> getUserByEmail(String email);

    List<User> findAllUsers();

    List<BasicUser> findAllBasicUsers();

    List<User> findUserByEmailFragment(String emailFragment);

    List<User> findUsersOlderThan(LocalDate date);
}
