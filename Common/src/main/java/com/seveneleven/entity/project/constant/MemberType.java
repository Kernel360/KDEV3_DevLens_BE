package com.seveneleven.entity.project.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberType {
    ADMIN("ADMIN"),
    CLIENT("client"),
    DEVELOPER("developer");

    final String description;
}
