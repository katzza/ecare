package com.ecare.services.validation;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = MyPasswordValidator.class)
@Documented
public @interface ValidPassword {

    String message() default "Password isn't correct";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
