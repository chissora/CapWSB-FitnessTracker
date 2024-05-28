package com.capgemini.wsb.fitnesstracker.user.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEmailDto {
    private Long id;
    private String email;
}
