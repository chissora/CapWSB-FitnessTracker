package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.BasicUser;
import com.capgemini.wsb.fitnesstracker.user.api.BasicUserDto;
import org.springframework.stereotype.Component;

@Component
public class BasicUserMapper {

    public BasicUserDto toDto(BasicUser basicUser) {
        return new BasicUserDto(basicUser.getId(), basicUser.getFirstName(), basicUser.getLastName());
    }

    public BasicUser toEntity(BasicUserDto basicUserDto) {
        return new BasicUser(basicUserDto.firstName(), basicUserDto.lastName());
    }
}
