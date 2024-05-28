package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailDto;
import org.springframework.stereotype.Component;

@Component
public class EmailUserMapper {

    public static UserEmailDto toDto(User user) {
        return new UserEmailDto(user.getId(), user.getEmail());
    }
}
