package com.seveneleven.entity.project.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberType {
    ADMIN("admin"),
    CLIENT("client"),
    DEVELOPER("developer");

    final String description;
}
