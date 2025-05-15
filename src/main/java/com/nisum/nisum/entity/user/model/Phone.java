package com.nisum.nisum.entity.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Phone {
    private String number;
    private String citycode;
    private String contrycode;
}
