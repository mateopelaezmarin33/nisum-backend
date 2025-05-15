package com.nisum.nisum.infrastructure.user.dto;

import com.nisum.nisum.entity.user.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class UserRegistrationData {
    String name;
    String email;
    String password;
    List<Phone> phones;
}
