package com.seveneleven.entity.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesNo {
    YES("Y"),
    NO("N");

    private final String value;
}