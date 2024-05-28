package com.capgemini.wsb.fitnesstracker.user.api;

import java.util.List;
import java.util.Optional;

public interface UserProvider {

    Optional<User> getUser(Long userId);

    Optional<User> getUserByEmail(String email);

    List<User> findAllUsers();

    List<BasicUser> findAllBasicUsers();
}
