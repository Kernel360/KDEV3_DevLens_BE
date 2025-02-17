package com.seveneleven.util.methodauthorize.annotation;

import org.springframework.security.access.prepost.PreAuthorize;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@projectAuthorizationCheckService.checkCustomerParticipant(principal.id, #projectId) or hasRole('ADMIN')")
public @interface CustomerParticipantAuthorize {
}
