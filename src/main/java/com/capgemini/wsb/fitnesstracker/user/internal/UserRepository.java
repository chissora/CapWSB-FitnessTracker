package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select user from User user where lower(user.email) like lower(concat('%', :emailFragment, '%'))")
    List<User> findEmailUserByEmailFragment(@Param("emailFragment") String emailFragment);

    @Query("select user from User user where user.birthdate < :earliestBirthdate")
    List<User> findUsersOlderThan(@Param("earliestBirthdate") LocalDate earliestBirthdate);
}
